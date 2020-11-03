<template>
  <div id="apitest">
    <button class="snap" v-on:click="capture">CAPTURE</button><br>
    <button class="snap" v-on:click="findface">FIND</button>
    <video style="width:600px; height:400px;" autoplay ref="video" id="video" class="video"></video>
    <!-- <button class="snap" v-on:click="capture()">SNAP</button> -->
    <canvas ref="canvas" class="canvas" id="canvas" width="600px" height="400px"></canvas>
    <!-- <img :src="imageFile.src"/> -->
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
      return {
        imageUrl: null,
        file : null,
        imageFile: "",
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
      // console.log(this.imageFile.src);
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
      var date = new Date();
      var fileName = date.getMonth()+1 +"" + date.getDate()+"" +date.getHours() +""+date.getMinutes() + ""+date.getSeconds()+".png";
      var file = this.dataURLtoFile(this.imageFile.src,fileName);
      console.log(file);

      var formData = new FormData();
      formData.append("image", file); 
      formData.append("email","test@test.com");
      // formData.append("email",store.state.userInfo.email); // 사용자 이메일

      axios.post(`http://localhost:9999/cobit/user/upload`, formData, { 
          headers: { 'Content-Type': 'multipart/form-data' } 
      }).then(response => {
        console.log(response.data);
        this.findface(response.data);
      });
    },
    findface(img){
      console.log(img);
      window.Kakao.API.request({
          url: `/v1/vision/face/detect`,
          data: {
          image_url: 'http://localhost'+img
          // image_url: 'http://localhost/img/1/profile/image.png'
          // image_url: 'http://localhost/img/1/profile/image3.png'
          },
          success: function (msg) {
            console.log(msg.result);
            var canvas = document.getElementById('canvas');
            var ctx = canvas.getContext("2d");
            canvas.getContext("2d").clearRect(0, 0, canvas.width, canvas.height);
            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.right_eye[0][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eye[1][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eye[2][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eye[3][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eye[4][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[4][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eye[5][0]*msg.result.width,msg.result.faces[0].facial_points.right_eye[5][1]*msg.result.height);
            ctx.fill();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.left_eye[0][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[1][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[2][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[3][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[4][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[4][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[5][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[5][1]*msg.result.height);
            ctx.fill();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.left_eyebrow[0][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[1][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[2][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[3][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[4][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[4][1]*msg.result.height);
            ctx.fill();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.right_eyebrow[0][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[1][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[2][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[3][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[4][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[4][1]*msg.result.height);
            ctx.fill();
          },
          fail: function(msg){
            console.log(msg);
          }
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