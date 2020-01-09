package com.graduation_project.testforum.provider;

import com.alibaba.fastjson.JSON;
import com.graduation_project.testforum.dto.AccessTokenDTO;
import com.graduation_project.testforum.dto.GitHubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            //System.out.println(s);
            String token = s.split("&")[0].split("=")[1];
            //System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUserDTO getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            GitHubUserDTO gitHubUserDTO = JSON.parseObject(s, GitHubUserDTO.class);
            Long id = gitHubUserDTO.getId();
            System.out.println(id);
            return gitHubUserDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
