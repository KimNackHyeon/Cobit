<template>
  <div>
    <button type="button" @click="apitest">api 테스트</button>
  </div>
</template>

<script>
export default {
  methods:{
    apitest(){
        
        window.Kakao.API.request({
            url:'/v2/vision/face/detect',
            headers:{
              'Content-Type': 'application/json'
            },
            data : {
              image_url:'/img/김낙현.jpg',
              // KakaoAK:'a90caea0345c3be6cd63ed25b9c44977'
            },
            success : res => {
              console.log(res);
              },
            fail : error => {
                console.log(error);
            }
        })
      },
  }
}
</script>

<style>

</style>