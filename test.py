import cv2
import matplotlib.pyplot as plt
import matplotlib.pyplot as mpimg
from keras.models import load_model

def predict(self, x):
    x = self.forward(x)
    return x

image = mpimg.imread('제목 없음.png')
model = load_model("cat_dog_model.h5")

labels_list = ['cat', 'dog']

IMAGE_SIZE = 64
reshaped_img = cv2.resize(image, (IMAGE_SIZE, IMAGE_SIZE)).reshape([1, IMAGE_SIZE, IMAGE_SIZE, 3])
accuracy = model.predict(reshaped_img)[0]

dic = {}

print("\n==================== 이미지 분류 결과 ====================")

for i in range(len(accuracy)):
    label = labels_list[i]
    dic[label] = accuracy[i]*100

dic = sorted(dic.items(), key=lambda x: x[1], reverse=True)
for key, value in dic:
    print(f"{key}\t{value:.2f} %")

print()

plt.imshow(image)
plt.axis('off')
plt.show()
