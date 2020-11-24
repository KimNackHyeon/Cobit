<template>
  <div id="apitest">
    <!-- <button class="snap" v-on:click="findface">FIND</button> -->
    <div class="videobox">
      <video style="width:20%; height:20%" autoplay ref="video" id="video" class="video"></video>
    </div>
    <!-- <button class="snap" v-on:click="capture()">SNAP</button> -->
    <div class="picturebtnbox">
      <!--  -->
      <!-- <button class="snap picturebtn" v-on:click="capture" @click.self="$emit('close')">사진찍기</button> -->
      <!-- <button class="snap picturebtn" v-on:click="capture" @click.self="$emit('close')">
        <v-icon class="cameraicon" @click.self="$emit('close')">mdi-camera</v-icon>
      </button> -->
      <button class="snap picturebtn" v-on:click="capture">
        <v-icon class="cameraicon">mdi-camera</v-icon>
      </button>
    </div>
    <div class="canvasbox" v-show="true">
      <canvas ref="canvas" class="canvas" id="canvas" width="500px" height="400px"></canvas>
    </div>
    
    <!-- <img :src="imageFile.src"/> -->
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'

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
      // // console.log(this.imageFile.src);
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
      // console.log(file);

      var formData = new FormData();
      formData.append("image", file); 
      formData.append("email","test@test.com");
      // formData.append("email",store.state.userInfo.email); // 사용자 이메일

      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/user/upload`, formData, { 
          headers: { 'Content-Type': 'multipart/form-data' } 
      }).then(response => {
        // console.log(response.data);
        this.findface(response.data);
      });
    },
    findface(img){
      // console.log(img);
      window.Kakao.API.request({
          url: `/v1/vision/face/detect`,
          data: {
          image_url: 'https://k3b102.p.ssafy.io'+img
          // image_url: 'https://k3b102.p.ssafy.io/img/1/profile/image.png'
          // image_url: 'https://k3b102.p.ssafy.io/img/1/profile/image3.png'
          },
          success: (msg) => {
            // console.log(msg.result);
            // 눈, 눈썹 unity 연결
            // console.log(msg.result.faces)
            if(msg.result.faces==undefined){
              Swal.fire(
                '얼굴 인식이 되지 않았습니다.',
                '다시 촬영해주세요.',
                'warning'
              )
            }
            else if(msg.result.faces.length > 1){
              Swal.fire(
                '한 사람만 사진찍을 수 있습니다',
                '',
                'warning'
              )
            }
            else{
              var facial_points = msg.result.faces[0].facial_points
              // 눈 변경
              var leftEyeDif = (facial_points.left_eye[5][1] - facial_points.left_eye[1][1])*2000
              var rightEyeDif = (facial_points.right_eye[5][1] - facial_points.right_eye[1][1])*2000
              // console.log('왼쪽눈' + leftEyeDif)
              // console.log('오른쪽눈' + rightEyeDif)
              if((0<=leftEyeDif && leftEyeDif<10) || (0<=rightEyeDif && rightEyeDif<10)){
                // console.log('실행')
                this.$emit('onChangeEye', 'eye4')
              }else if(leftEyeDif<30 || rightEyeDif<30){
                this.$emit('onChangeEye', 'eye3')
              }else{
                this.$emit('onChangeEye', 'eye2')
              }
              // 눈썹 변경
              var leftEyebrowDif = (facial_points.left_eyebrow[0][1] - facial_points.left_eyebrow[4][1])*2000
              var rightEyebrowDif = (facial_points.right_eyebrow[0][1] - facial_points.right_eyebrow[4][1])*2000
              // console.log('왼쪽눈썹' + leftEyebrowDif)
              // console.log('오른쪽눈썹' + rightEyebrowDif)
              if((-2<=leftEyebrowDif && leftEyebrowDif<=2) || (-2<=rightEyebrowDif && rightEyebrowDif<=2)){
                this.$emit('onChangeEyebrow', 'eyebrow1')
              }else if(leftEyebrowDif>2 || rightEyebrowDif>2){
                this.$emit('onChangeEyebrow', 'eyebrow2')
              }else{
                this.$emit('onChangeEyebrow', 'eyebrow3')
              }

            }
            // console.log(msg.result);
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
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.left_eye[0][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[1][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[2][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[3][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[4][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[4][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eye[5][0]*msg.result.width,msg.result.faces[0].facial_points.left_eye[5][1]*msg.result.height);
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.left_eyebrow[0][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[0][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[1][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[1][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[2][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[2][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[3][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[3][1]*msg.result.height);
            ctx.lineTo(msg.result.faces[0].facial_points.left_eyebrow[4][0]*msg.result.width,msg.result.faces[0].facial_points.left_eyebrow[4][1]*msg.result.height);
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.right_eyebrow[0][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[0][1]*msg.result.height);
            msg.result.faces[0].facial_points.right_eyebrow.forEach(element => {
              ctx.lineTo(element[0]*msg.result.width,element[1]*msg.result.height);
            });
            
            // ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[1][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[1][1]*msg.result.height);
            // ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[2][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[2][1]*msg.result.height);
            // ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[3][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[3][1]*msg.result.height);
            // ctx.lineTo(msg.result.faces[0].facial_points.right_eyebrow[4][0]*msg.result.width,msg.result.faces[0].facial_points.right_eyebrow[4][1]*msg.result.height);
            ctx.stroke();

            // ctx.moveTo(element[0]*msg.result.width,element[1]*msg.result.height);
            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.jaw[0][0]*msg.result.width,msg.result.faces[0].facial_points.jaw[0][1]*msg.result.height);
            msg.result.faces[0].facial_points.jaw.forEach(element => {
              ctx.lineTo(element[0]*msg.result.width,element[1]*msg.result.height);
            });
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.lip[0][0]*msg.result.width,msg.result.faces[0].facial_points.lip[0][1]*msg.result.height);
            msg.result.faces[0].facial_points.lip.forEach(element => {
              ctx.lineTo(element[0]*msg.result.width,element[1]*msg.result.height);
            });
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(msg.result.faces[0].facial_points.nose[0][0]*msg.result.width,msg.result.faces[0].facial_points.nose[0][1]*msg.result.height);
            msg.result.faces[0].facial_points.nose.forEach(element => {
              ctx.lineTo(element[0]*msg.result.width,element[1]*msg.result.height);
            });
            ctx.stroke();
          },
          fail: function(){
            // console.log(msg);
            Swal.fire(
              '얼굴이 인식되지 않았습니다.',
              '다시 촬영해주세요.',
              'warning'
            )
          }
          });
    }


  },
  beforeMount(){
    this.init();
  }
}
</script>

<style scoped>
.videobox {
  display: flex;
  justify-content: center;
  /* padding-top: 5%; */
}
.picturebtnbox {
  display: flex;
  justify-content: center;
  align-items: center;
  /* margin-top: 5%; */
}
.picturebtn {
  width: 25%;
  height: 12%;
  background-color: white;
  font-size: 27px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
  transition: box-shadow .3s ease;
  /* box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff; */
  position: absolute;
  bottom: 2%;
}
.canvasbox {
  display: flex;
  justify-content: center;
}
.cameraicon {
  color: black;
  font-size: 38px;
}
</style>