package com.example.apigateway.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.apigateway.dto.GetImageRequest;
import com.example.apigateway.dto.GetImageResponse;

@Controller
@RequestMapping
public class ApiGatewayController {

	// AWS API GatewayのURLをapplication.propertiesから注入
	@Value("${aws.api.gateway.url}")
	private String awsApiGatewayUrl;

	// REST API呼び出し用のテンプレート
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 画像取得画面を表示する
	 * @return 画像取得画面のテンプレート名
	 */
	@GetMapping("/getImageSoap")
	public String showLoginForm() {
		return "getImage";
	}

	/**
	 * API Gateway経由で文字列変換を行う
	 * @param request 変換リクエストDTO
	 * @return 変換結果を含むレスポンスエンティティ
	 */
	@GetMapping("/ApiGateway")
	@ResponseBody
	public ResponseEntity<?> imageGateway(@ModelAttribute GetImageRequest request) {
		try {
			String url = awsApiGatewayUrl + "?imageName="
					+ URLEncoder.encode(request.getImageName(), StandardCharsets.UTF_8);

			// AWS API Gatewayにリクエストを送信
			GetImageResponse response = restTemplate.getForObject(
					url,
					GetImageResponse.class);

			// 結果に応じてHTTPステータスを決定
			HttpStatus status = response.getImageData() == null ? HttpStatus.NOT_FOUND
					: HttpStatus.OK;

			// 結果を返す
			if (response.getImageData() != null) {

				return ResponseEntity.status(status).body(response);// 成功レスポンス
			} else {

				return ResponseEntity.status(status).body("画像取得が存在しない：" + request.getImageName());// 成功レスポンス
			}

		} catch (Exception e) {
			// エラー発生時の処理
			return ResponseEntity.internalServerError().body(e.getMessage()); // エラーレスポンス

		}
	}
}
