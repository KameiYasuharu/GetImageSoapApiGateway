package com.example.apigateway.dto;

/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 */

public class GetImageRequest {

	private String imageName;

	/**
	 * imageNameプロパティの値を取得します。
	 * 
	 * @return
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * imageNameプロパティの値を設定します。
	 * 
	 * @param value
	 */
	public void setImageName(String value) {
		this.imageName = value;
	}

}
