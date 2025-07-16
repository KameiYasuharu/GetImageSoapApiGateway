async function loadImage() {
	const imageName = document.getElementById('imageName').value.trim();
	const errorElement = document.getElementById('error');
	const imagePreview = document.getElementById('imagePreview');

	errorElement.textContent = '';
	imagePreview.style.display = 'none';

	if (!imageName) {
		errorElement.textContent = '画像名前を入力してださい！';
		return;
	}

	try {
		const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
		const response = await fetch(`${basePath}/ApiGateway?imageName=${encodeURIComponent(imageName)}`,  {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			}
		})

		if (!response.ok) {
			const error = await response.text();
			throw new Error(error || '画像取得が失敗しました');
		}

		const result = await response.json();
		if (!result.imageData) {
			throw new Error('取得した画像が表示できません');
		}

		const mimeType = result.mimeType || 'image/jpeg';
		imagePreview.src = `data:${mimeType};base64,${result.imageData}`;
		imagePreview.alt = result.imageName || 'Loaded image';
		imagePreview.style.display = 'block';



	} catch (error) {
		errorElement.textContent = error.message;
	}
}