<template>
  <div>
    <div class="changecharacter">
        <div class="header">
          <div class="back-btn" @click="onBack"><i class="fas fa-chevron-left"></i>돌아가기</div>
        </div>
      <div class="boxs">
        <!-- 캐릭터 꾸미기 -->
        <div class="leftbox">
          <div class="mycharacterbox">
            <span class="itemtitle">내가 가진 캐릭터</span>
            <div class="mycharacters">
              <img v-for="i in 10" :key=i src="../assets/images/penguin2.png" alt="character1">
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">색깔</span>
            <div class="items">
              <div v-for="(color, i) in colors" :key="i" :style="{background: color.name}" class="color" @click="onChangeColor(color.name)"></div>
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">눈</span>
            <div>
              <!-- <img src="" alt=""> -->
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">입</span>
          </div>
          <div class="itembox">
            <span class="itemtitle">아이템</span>
          </div>
          <div class="camera">
              <v-icon class="cameraimg">mdi-camera</v-icon>
              <router-link to="/apitest">
               <button> 내 얼굴로 캐릭터 만들기</button>
              </router-link>
              
          </div>
        </div>
        <!-- 캐릭터 보여주는 곳 -->
        <div class="rightbox">
          <div class="character">
            <unity src="../unity2/Build/unity2.json" unityLoader="#" ref="myInstance"></unity>
          </div>
          <div class="savebox">
            <div class="save-btn" @click="saveItem">저장하기</div> 
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '../css/changecharacter.scss';
import Unity from 'vue-unity-webgl';
// import SendMessage from 'vue-unity-webgl';
// import UnityLoader from '../../public/unity2/Build/UnityLoader.js';
import axios from 'axios';
import store from '../vuex/store';

export default {
  components: {
    Unity
  },
  data() {
    return {
      // colors: ["black", "red", "yellow", "green", "blue", "purple"],
      colors: [],
      eyes: [],
      mouses: [],
      myItems: [],
      // myColor:[]
    }
  },
  methods: {
    onBack() {
      this.$router.push('/mypage')
    },
    onChangeColor(color) {
      console.log(color)
      // var gameInstance = UnityLoader.instantiate("gameContainer", "Build/unity2.json");
      // SendMessage('pen_before_jump/body', 'ChangeColor', color);

      this.$refs.myInstance.message('body', 'ChangeColor', color);

      this.myItems.color = color;
    },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      console.log("캐릭터 정보 불러오기");
      axios.get(`http://localhost:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        this.myItems = res.data;
        // console.log(this.myItems);
        this.onChangeColor(this.myItems.color);
      })
    },
    saveItem(){
      axios.post(`http://localhost:9999/cobit/product`,this.myItems)
      .then(()=>{
        this.$router.push('/mypage');
      });
    }
  },
  created(){
    if(this.$cookies.isKey("access_token")){
      window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              const kakao_account = res.kakao_account;
              axios.get(`http://localhost:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                // console.log(res);
                this.$store.commit('setKakaoUserInfo', res.data);
              })
          },
      })

      axios.get(`http://localhost:9999/cobit/product`)
      .then(res => {
        console.log(res);
        res.data.forEach(item => {
          if(item.type == 1){ // 캐릭터색 아이템 불러오기
            this.colors.push(item);
          }
        });
      });

    } else{
      this.$router.push('/');
    }
  },
  mounted(){
    setTimeout(() => {
      // console.log(this.items);
      this.loadMyCharacter();
    }, 2000);
  }
}
</script>

<style scoped>
.changecharacter {
  padding: 4vh;
}
.header .back-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 150px;
  height: 50px;
  background-color: #a4d4ff;
  font-size: 22px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}
.header .back-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
.header .back-btn .fa-chevron-left {
  margin: 0;
  margin-right: 10px;
}
.boxs {
  overflow: hidden;
  margin-top: 1vh;
}
.leftbox {
  float: left;
  padding: 3% 2% 0;
  width: 50%;
}
.mycharacterbox {
  height: 12vh;
  border: 2px solid black;
  border-radius: 15px;
  margin-bottom: 2vh;
  position: relative;
}
.mycharacters {
  overflow-x: auto;
  overflow-y: hidden;
  height: 12vh;
  display: flex;
  justify-content: left;
  align-items: center;
  padding: 0 20px;
}
.mycharacters img {
  width: 60px;
  margin-right: 1vw;
}
.itembox {
  height: 10vh;
  border: 2px solid black;
  border-radius: 15px;
  margin-bottom: 2vh;
  position: relative;
}
.itemtitle {
  position: absolute;
  background-color: white;
  top: -10px;
  left: 10px;
  font-size: 18px;
  font-family: 'BMJUA';
  padding: 0 2%;
}
.items {
  overflow-x: auto;
  overflow-y: hidden;
  height: 10vh;
  display: flex;
  justify-content: left;
  align-items: center;
  padding: 0 20px;
}
.color {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 2vw;
}
/* .color:hover {
  box-shadow: 0 0 15px black;
} */
.rightbox {
  float: left;
  padding: 3% 2%;
  width: 50%;
}
.character {
  height: 60vh;
  margin-bottom: 2vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.camera {
  font-size: 28px;
  font-family: 'BMJUA';
  height: 10vh;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.cameraimg {
  font-size: 55px;
  margin-right: 10px;
  color: black;
}
.savebox {
  height: 10vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.save-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 350px;
  height: 60px;
  background-color: #a4d4ff;
  font-size: 27px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}
.save-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
</style>