# 와이어바알리 Android 앱 과제

환율 계산 앱

### 기간

22년 12월 5일 12:00 ~ 12월 13일 12:00

### 실행영상

https://user-images.githubusercontent.com/15232122/207077783-b50a8c64-ce11-478f-87e8-4bd8187f1cdc.mp4


### API 키 적용 관련 사항

`local.properties` 파일 하단에 다음 내용을 추가합니다.

```
api.key="{API_KEY}"
```

### 요구사항

- 송금국가는 미국으로 고정입니다. 통화는 미국달러(USD)입니다.
- 수취국가는 한국, 일본, 필리핀 세 군데 중 하나를 여러가지 방법을 이용하여 선택합니다. 각각 통화는 KRW, JPY, PHP 입니다.
- 수취국가를 선택하면 아래 환율이 바뀌어나타나야 합니다. 환율은 1 USD 기준으로 각각 KRW, JPY, PHP의 대응 금액입니다.
- 송금액을 USD로 입력하면 아래 수취금액이 KRW, JPY, PHP 중 하나로 계산되어서 나와야 합니다.
- 환율과 수취금액은 소숫점 2째자리까지, 3자리 이상 되면 콤마를 가운데 찍어 보여줍니다. 예를 들어 1234라면 1,234.00으로 나타냅니다.
- 환율정보는 https://currencylayer.com/ 의 무료 서비스를 이용해서 실시간으로 가져와야 합니다.
- 새로운 무료 계정을 등록해서 API 키를 받아서 사용하면 됩니다. 샘플로 등록된 계정의 키를 예를 들면 다음과 같습니다. http://www.apilayer.net/api/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c
- 환율은 앱이 시작될 때 한번 가져와서 계속 사용해도 되고, 혹은 수취국가가 변경될때 마다 API로 서버에 요청해서 새로운 환율 정보를 가져와도 됩니다.
- 송금액을 입력할때마다 수취금액을 계산하거나 별도로 '계산하기'' 버튼을 추가하여 선택된 수취국가와 그 환율, 그리고 송금액을 가지고 수취금액을 계산해서 하단에 보여주면 됩니다.
- 수취금액이 0보다 작은 금액이거나 10,000 USD 보다 큰 금액, 혹은 바른 숫자가 아니라면 “송금액이 바르지 않습니다"라는 에러 메시지를 보여줍니다. 메시지는 팝업, 혹은 하단에 빨간 글씨로 나타나면 됩니다.

### 사용한 라이브러리

- Foundation
  + AppCompat
  + Android KTX
- Architecture
  + ViewBinding
  + ViewModel
  + Navigation
  + Room
  + WorkManager
- UI
  + ConstraintLayout
- Etc
  + Hilt : 의존성 주입 라이브러리
  + Retrofit2 & OkHttp3 : 네트워크 라이브러리
  + Kotlin Coroutine & Flow : 비동기 처리 라이브러리
  + Mokito : 테스트코드 작성을 위한 프레임워크

### 참조
- [github.com/android/sunflower](https://github.com/android/sunflower)
