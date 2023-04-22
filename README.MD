<img src="https://github.com/okanekudasai/pictures/blob/master/%EA%B7%B8%EB%A6%BC1.png?raw=true" width="128" height="128"/>


# 🏃🏻 1. Project 소개 및 구현 화면
> **🧭 프로젝트 기간**
>
> - `2월 27일` ~ `4월 7일`


&nbsp;


### 💰 Project 소개

**🗡️ 주제 선정 계기**

> ### 1. 투명성
> 블록체인 정보는 모든 참여자가 볼 수
있으며 변경이 불가능합니다.
>### 2. 경제성
>중앙 서버와 시스템 관리 비용이
들어가지 않아 비용이 적게 듭니다.
>### 3. 보안성
>암호화된 데이터와 키 값으로만 거래가
이루어지기 때문에 보안성이 높습니다


&nbsp;

&nbsp;
  

### 💰 챌린멍스 앱 설명

[챌린멍스 앱 화면 (클릭시 페이지 이동)](./README.assets/구현화면.md)
  

&nbsp;

&nbsp;

  
  

### 💰 서비스 주요 기능
  
- **서비스 설명** : **기부를 재미있게!** 반려동물들을 위한 기부 플랫폼. 반려동물들을 키우는 챌린멍스 유저들과 함께 챌린지에 도전하고 블록체인 기술을 활용한 기부까지!
- **주요 기능**
  - **`블록체인`을 활용하여 투명성, 경제성, 보안성을 갖춘 기부 플랫폼의 기능 및 유저가 직접만드는 일반 챌린지와 판넬뒤집기 챌린지, 기부 캠페인의 개설과 기부**
  - `KLAYTON`과 `CYPRESS`메인넷을 사용하여 클레이튼을 전송하고 주고 받기가 가능합니다.
  - 유저들이 직접 타 유저의 챌린지 수행의 적합성 여부를 판단합니다.
  - 실시간 위치 정보 기반 특별 챌린지를 수행합니다.
  - 사용자의 챌린지 참여 실적에 따른 보상을 제공합니다.



&nbsp;

&nbsp;


  

# 🏃🏻 2. 개발 환경  

### 💰 아키텍처

![기술스택](https://github.com/okanekudasai/pictures/blob/master/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98.png?raw=true)

&nbsp;

**🗡️ Android**
- `Android Studio`
- `Glide`
- `Retrofit2`
- `OkHttp3`
- `Jetpack`



&nbsp;


  

**🗡️ Backend**

- `java 11`
- `spring boot 2.7.8`
- `gradle-7.6-bin`
- `Intellij`
- `mysql`
- `websocket`

  

&nbsp;


  

**🗡️ DevOps(CI/CD)**

- `aws ec2`
- `docker`
- `nginx`
- `jenkins`



  
  

&nbsp;


&nbsp;

  
  

### 💰 기술 특이점


**🗡 Klayton API 및 Cypress 메인넷의 활용**

- `Klayton.api`에서 제공하는 api를 활용하여 메인넷에 직접 요청을 넣어 계좌간 클레이튼의 거래가 가능하도록 하여  기부기능을 구현하였습니다. 
- 계좌 생성 및 계좌간 클레이튼 전송 및 잔액 조회, 거래 내역 조회가 가능하도록 구현하였습니다.

**🗡 WebSocket 기술 및 scheduler 활용**

- `websocket기술`을 활용하여 각 컴포넌트 별로 다양한 이벤트 들을 트리거하여 클라이언트간의 동시적인 정보교환을 가능하게 했습니다.
- -`scheduler`과 `cron표현식`을 사용하여 특정 시간에 자동으로 컴포넌트가 실행되도록 하였습니다. 

&nbsp;


**🗡️ Docker, Jenkins를 이용한 CD 구축 및 SSL 인증서 적용**

`Docker image` `Jenkins`의 `pipeline`을 이용하여 자동 배포를 구축했습니다. `Gitlab webhook`을 설정하여 Jenkins에 빌드 트리거를 설정했으며 Gitlab에서 master 브랜치에 push하면 자동으로 배포될 수 있도록 구축하여 개발하는 과정에서 배포로 인한 시간 낭비를 줄였습니다.

- `backend`, `springBoot`에서는 `docker image`로 빌드한 후 배포
- `nginx`와 `letsencrypt`를 통해 `ssl` 인증서를 적용했고 `backend`에서는 `/api`로 `80(http)`, `443(https)` 로 프록시를 분리


했습니다.

  
  

&nbsp;

  

&nbsp;

  

# 🏃🏻 3. 협업 툴

  

> **🥁 챌린멍스 협업 툴**
> 
> 1. [notion](https://tame-soarer-696.notion.site/2-7abb387ef23f4994ae5debac22771631)
> 2. zira
> 3. mattermost
> 4. slack




&nbsp;



  
  

### 💰 프로젝트 개발 단계

  

[figma (클릭시 페이지 이동)](https://www.figma.com/file/NcE3q5X1CWMxW8Erz5K4Tj/%EB%94%94%EC%9E%90%EC%9D%B8?node-id=0%3A1&t=Hjqf5aPWJZPeaYyU-1)

  

&nbsp;


&nbsp;

  


  

# 🏃🏻 4. 프로젝트 회고
<img src="https://github.com/okanekudasai/pictures/blob/master/KakaoTalk_20230417_071420187_02.jpg?raw=true" width="250"/>


**🦉 이아름**

>이번 프로젝트때 처음 해보는 일이 많았었는데 너무 좋은 팀원들과 체계적으로 정해놓은 규칙들 덕분에 프로젝트를 잘 마무리 할 수 있었던 것 같습니다. 또한 클린 아키텍처와 웹소켓을 활용할 수 있는 좋은 경험이었습니다.


&nbsp;


**🐧 강은선**

>안드로이드 팀 리더로 참여한 첫 프로젝트인만큼 부담감도 있었지만 서로 개발을 하면서 지킬 규칙을 세우고 이를 잘 지켜나가면서 프로젝트를 잘 마칠 수 있었습니다. 카카오 로그인과 viewpager2를 처음 사용해보는데다 블록체인이라는 새로운 기술을 도입하여 적응하는데 조금 어려움이 있었지만 공식문서와 chatGPT의 활용으로 어려움을 극복할 수 있었습니다.


&nbsp;



**🐭 김부경**

>여따 적으면 될듯



&nbsp;



**🐰 변윤경**

>여따 적으면 될듯


&nbsp;



**🐶 이동하**

>안드로이드 개발과 클린 아키텍처를 경험해 볼 수 있었던 정말 의미있는 프로젝트 였습니다. 처음엔 적응하기가 굉장히 힘들었지만 클래스간의 의존성을 낮추고 유지 보수성을 높혀 줄 수 있는 좋은 구조라고 생각이 들었습니다! 또한 백엔드에서도 JPA과 query dsl을 사용해 보고 익숙해 지며 그 강력한 기능을 맛볼 수 있어 좋은 경험이었다고 생각합니다.


&nbsp;



&nbsp;

