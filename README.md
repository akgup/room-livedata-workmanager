### Required Component
- Retrofit2
- Okhttp3
- Room Database
- LiveData
- WorkManager

### Setup mock server <br />
Navigate https://webhook.site and copy url like below <br />
https://webhook.site/fa4888ac-ad91-4d08-b3fd-b05ffe306e1e <br />

Configure api resource with above <br />

```
    @Headers("Content-Type: application/json")
    @POST("/fa4888ac-ad91-4d08-b3fd-b05ffe306e1e")
    Call<ResponseBody> addNote(@Body Note note);
  ```

now webhook is ready to receive sync request from app. 
