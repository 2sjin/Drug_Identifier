import matplotlib.pyplot as plt
from keras.models import load_model
from PIL import Image, ImageOps
import numpy as np

IMG_SIZE = 224

# 텍스트 파일을 읽은 후, 딕셔너리에 (인덱스번호, 클래스명) 저장
class_dic = {}
with open("model/labels.txt", 'r', encoding="UTF-8") as f:
    for line in f:
        (k, v) = line.split()
        class_dic[int(k)] = v

# 모델 및 테스트 이미지 불러오기
model = load_model('model/keras_model.h5')
image = Image.open('test.png').convert("RGB")   # 이미지의 투명도를 무시하고 RGB 채널만 불러오기
size = (IMG_SIZE, IMG_SIZE)
image = ImageOps.fit(image, size, Image.ANTIALIAS)    # 이미지 크기 조정

# 이미지를 넘파이 배열에 적용
data = np.ndarray(shape=(1, IMG_SIZE, IMG_SIZE, 3), dtype=np.float32) # 넘파이 배열 생성
image_array = np.asarray(image)                             # 이미지를 넘파이 배열에 적용

# 이미지를 정규화하여 배열에 저장
normalized_image_array = (image_array.astype(np.float32) / 127.0) - 1
data[0] = normalized_image_array

# 이미지 예측 실행
prediction = model.predict(data)[0]

# 이미지 예측 결과 출력
print("\n==================== 이미지 예측 결과 ====================")

# (클래스명, 정확도(%))가 저장될 딕셔너리
sorted_accuracy_dic = {}

# 딕셔너리에 (클래스명, 정확도(%)) 저장
for key, value in class_dic.items():
    sorted_accuracy_dic[value] = prediction[key]*100    

# 딕셔너리를 정확도(%) 기준으로 내림차순 정렬하여 출력
sorted_accuracy_dic = sorted(sorted_accuracy_dic.items(), key=lambda x: x[1], reverse=True)
for key, value in sorted_accuracy_dic:
    print(f"{key}\t{value:.2f} %")
print()

# 테스트 이미지 출력
plt.imshow(image)
plt.axis('off')
plt.show()

# (클래스명, 정확도(%)) 딕셔너리를 외부로 전달
# 구현 예정
print(sorted_accuracy_dic)