# 사진으로 의약품 식별 검색하기<br>(Search for Drugs by Photography)
![image](https://user-images.githubusercontent.com/91407433/175778959-7e800856-61a1-4a63-9a76-1f35e581f850.png)
- 기여자: 이승진, 조준호

## 산출물
[☞ 프로젝트 결과보고서](https://drive.google.com/file/d/1yyf5m6vsfhbuc2hW-h3Wz_Pk38vHf5so/view?usp=sharing)

## 목표
단말기와 연결된 카메라 혹은 기억장치에 저장된 이미지 파일을 분석하여 의약품을 식별 검색하는 안드로이드 애플리케이션을 구현하는 것을 목표로 프로젝트를 진행한다.

## 운영체제(OS)
Android 8.0(Oreo) 이상 버전에 최적화됨.

## 소프트웨어 설계

### 소프트웨어 전체 구성도
|![image](https://user-images.githubusercontent.com/91407433/175778983-512e8711-f8dd-48b0-b128-a170437f92a5.png)|
|:--|

### 릴레이션 스키마
||
|:--|
|의약품 릴레이션<br>(__Class ID__, 제품명, 성분/함량, 식약처 분류, 복용정보, URL, 이미지 URL)|

※ 기본키는 __Class ID__

## 기계 학습
### 이미지 데이터 수집
|![image](https://user-images.githubusercontent.com/91407433/175780202-c389e01c-e3c0-4528-9f22-16f09e5dec02.png)|
|:-:|
### 기계 학습 및 모델 생성
|![image](https://user-images.githubusercontent.com/91407433/175780156-88406da4-1a40-48da-a167-243bb76168ca.png)|
|:-:|
|![image](https://user-images.githubusercontent.com/91407433/175780167-b63847c6-3163-4529-8c94-cd70d9cf7548.png)|

## 소프트웨어 구현
### 애플리케이션 구현

|로딩 UI|초기 UI|카메라 UI|
|:-:|:-:|:-:|
|![image](https://user-images.githubusercontent.com/91407433/175778766-883771c6-9c9f-4765-af8f-bc7f9e4e096a.png)|![image](https://user-images.githubusercontent.com/91407433/175778771-76547d57-759c-48a9-9f78-d27e64cb6a27.png)|![image](https://user-images.githubusercontent.com/91407433/175778802-b50a9e98-0409-452a-aabc-7505f84e5809.png)|
|초기 로딩 화면(약 1~2초)|로딩 완료 후, [사진 검색 시작하기]<br>버튼을 눌러 카메라를 실행할 수 있다.|카메라에 원하는 의약품이 보이게 한 후,<br>하단의 셔터 버튼을 누르면<br>검색 결과가 리스트로 출력됨.|

|리스트 UI|의약품 정보 UI|약학정보원 웹 UI|
|:-:|:-:|:-:|
|![image](https://user-images.githubusercontent.com/91407433/175778803-a02764ad-61eb-4ba3-b26c-f45e330f1426.png)|![image](https://user-images.githubusercontent.com/91407433/175778845-8d53b325-8ca3-46ca-a6c7-bc5263ad1ea5.png)|![image](https://user-images.githubusercontent.com/91407433/175778885-bd39e6f6-067c-412a-bc3e-40214b217f66.png)|
|리스트 내 원하는 항목 선택 시,<br>해당 의약품에 대한 정보를 확인할 수 있음.|의약품 이미지 및 요약된 정보를 확인할 수 있음.<br>[의약품 상세정보(약학정보원)]<br>버튼을 누르면 상세정보 확인 가능함.|약학정보원 홈페이지 내 해당 의약품<br>상세정보 페이지에 연결된다.|


### 데이터베이스 구현(MySQL Workbench)
|![image](https://user-images.githubusercontent.com/91407433/175779116-8fb87a92-72af-4219-9615-cf8486715ee8.png)|
|:-:|
|![image](https://user-images.githubusercontent.com/91407433/175779152-2e506bf0-3054-4101-9af5-e1ccc38b3500.png)|

