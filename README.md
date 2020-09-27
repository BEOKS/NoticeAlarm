
# 공지사항 알림 어플리케이션
## 개발 일정
### 1. 요구사항 분석 (9월 23일)
	1.  공지사항을 보고싶은 사이트의 URL을 입력하면 데이터를 크롤링하는 기능
    
	2.  원하는 사이트들의 공지를 한 곳에 모아 볼 수 있는 기능
    
	3.  주기적으로 데이터를 갱신하여 변화를 감지하면 알람을 알려주는 기능
    
	4.  등록한 url을 삭제하는 기능
    
	5.  알람을 켜고/끄는 기능
    
	6.  url 카테고리 추가 및 삭제 기능
    
	7.  (부가) 사이트 url 공유하기 기능
    
	8.  (부가) 태그를 등록하여 해당 태그가 포함된 공지사항만 알려주는 기능
### 2. 화면 설계서 초안 작성(9월 23일)
### 3. 기초 계획서 작성(예정)
	1. 요약정보 작성
	2. 개발배경 작성
	3. 클래스 다이어그램 작성
	4. 개발일정 작성
### 4. 레이아웃 구현(예정)
### 5. 레이아웃 동적 구현(예정)
### 6. 웹 크롤링 서버 구현
## 기초계획서
### 1.시스템 구조
![image](https://user-images.githubusercontent.com/30094719/94089017-f3d4ca80-fe4c-11ea-9d0e-89ceffbe3eb5.png)
### 2.어플리케이션 클래스 다이어그램
![image](https://user-images.githubusercontent.com/30094719/94145879-1ea33b00-feae-11ea-9b33-11e9b787df8f.png)
#### 1.MainActivity : 시작 시 실행화면
##### 필드
+pageTabLayout : 하나의 탭에 선택한 URL페이지의 공지사항을 내부에 보여줌
+floatingActionButton : 버튼 클릭시 현재 선택한 카테고리에 새로운 URL을 추가하도록하는 URLDialog를 호출
+trashButton : 클릭하면 현재 표시된 카테고리를 삭제하는 AlertDialog를 호출
+bottomAppbar : 액티비티의 아랫부분에 위치한 메뉴창
##### 메서드
settingView() : 액티비티 시작 시 SharedPreference에서 데이터를 가져와 모든 뷰를 초기설정함
#### 2.PageTabLayout : 공지사항을 보여줄 탭 레이아웃 (extends TabLayout)
##### 메서드
+ addNewURL(String): bool  :새로운 URL을 입력받아 Fragment를 생성한 후 ServerConnect.addAlaram(url,fragment)을 호출
+ deleteURL(String):bool :  해당 URL의 Fragment를 삭제 한 후 ServerConnect.deleteAlarm(url)을 호출
+ updateData(Fragment , String data) : 데이터를 입력하면 Fragment에 해당 데이터의 내용을 입력
#### 3. CustomBottomAppBar : 액티비티 아래 메뉴 부분 (extends BottomAppBar)
##### 필드
+ bottomDrawer: BottomDrawer : 카테고리 목록 및 카테고리 추가 버튼이 있는 Drawer
##### 메서드
setNavigationButton() : 네비게이션 버튼을 클릭하면 BottomDrawer가 올라오도록 함
#### 4. CategoryBottomSheetDialog : 카테고리 선택 부분 (extends BottomSheetDialog)
##### 필드
+ choosedCategory: String  : 현재 선택된 카테고리의 이름
+ categoryList : ArrayList<String> :  카테고리 이름 리스트
##### 메서드
+ chooseCategory(String): void : 카테고리 선택 시 페이지 탭 레이아웃의 내용 변경
+ addNewCategory(String) :void : CategoryDialog 실행 및 추가 선택 시 새로운 카테고리 추가
+ deleteCategory(String): void : 선택한 카테고리 삭제 및 포함된 URL데이터 삭제 및 알림 해제
#### 5. ServerConnect: Fifrebase 서버와 통신하는 클래스
##### 필드
- database: FirebaseDatabase : 파이어베이스 인스턴스
##### 메서드
+addAlarm(String,Fragment) : bool : 파이어베이스에 새로운 URL을 추가하고 스냅샷에 Fragment를 입력하여 DB 데이터가 변경될 경우 Fragment데이터도 변경함

+deleteAlarm(String) : bool : 파이어베이스 URL부분에서 user의 token 삭제함, User/URL에 더 이상 user가 없을 경우 해당 URL또한 삭제함
