<template>
  <div>
    <video style="width:300px; height:300px;" autoplay ref="video" id="video" class="video"></video>
    <!-- <button class="snap" v-on:click="capture()">SNAP</button> -->
    <canvas ref="canvas" class="canvas" id="canvas" width="300px" height="300px"></canvas>
    <button class="snap" v-on:click="capture">SNAP</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
        return {
            imageUrl: null,
            file : null,
            imageFile: null,
        }
    },
  methods:{
      init() {
      if (
        "mediaDevices" in navigator &&
        "getUserMedia" in navigator.mediaDevices
      ) {
        let constraints = {
          video: {
            width: {
              min: 640,
              ideal: 1280,
              max: 1920,
            },
            height: {
              min: 360,
              ideal: 720,
              max: 1080,
            },
          },
        };
        navigator.mediaDevices.getUserMedia(constraints).then((stream) => {
          this.videoPlayer = document.querySelector("video");
          this.videoPlayer.srcObject = stream;
          this.videoPlayer.play();
        });
      } else {
        alert("Cannot get Media Devices");
      }
    },
    capture(){
      this.video = this.$refs.video;
      this.canvas = this.$refs.canvas;
      this.canvas.getContext("2d").drawImage(this.video, 0, 0, this.canvas.width, this.canvas.height);
      this.imageFile = new Image();
      this.imageFile.src = this.canvas.toDataURL();
      this.uploadImage();
    },
    dataURLtoFile(dataurl, fileName){
        var arr = dataurl.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), 
            n = bstr.length, 
            u8arr = new Uint8Array(n);
            
        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }
        
        return new File([u8arr], fileName, {type:mime});
    },
    uploadImage() {
      var file = this.dataURLtoFile(this.imageFile.src,'image.jpg');
      console.log(file);

      var formData = new FormData();
      formData.append("image", file); // 변경할 프로필 사진
      formData.append("email","test@test.com");
      // formData.append("email",store.state.userInfo.email); // 사용자 이메일

      axios.post(`http://k3b102.p.ssafy.io:9999/cobit/user/upload`, formData, { 
          headers: { 'Content-Type': 'multipart/form-data' } 
      }).then(response => {
        // // console.log(response);
        // this.image = response.data;
        console.log(response.data);
        window.Kakao.API.request({
          url: `/v1/vision/face/detect`,
          data: {
          image_url: 'http://k3b102.p.ssafy.io'+response.data
          },
          success: function (msg) {
          console.log(msg);
          },
          fail: function(msg){
            console.log(msg);
          }
          });
      });
    }


  },
  beforeMount(){
    this.init();
  }
}
</script>

<style>

</style>