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
              <img src="../assets/images/penguin.png" alt="character1">
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">색깔</span>
            <div class="items">
              <div v-for="(color, i) in colors" :key="i" :style="{background: color.name}" class="color" @click="onChangeColor(color.name)"></div>
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">눈썹</span>
            <div class="eyebrowbox">
              <div class="noEyebrow" @click="onChangeEyebrow('eyebrow0')">없음</div>
              <img v-for="i in 3" :key="i" :src="require(`../assets/images/eyebrow${i}.png`)" :alt="`eyebrow${i}`" @click="onChangeEyebrow(`eyebrow${i}`)">
            </div>
          </div>
          <div class="itembox">
            <span class="itemtitle">눈</span>
            <div class="eyebox">
              <img v-for="i in 4" :key="i" @click="onChangeEye(`eye${i}`)" :src="require(`../assets/images/eye${i}.png`)" :alt="`eye${i}`">
            </div>
          </div>
          <div class="itembox" style="height: 16vh">
            <span class="itemtitle">아이템</span>
            <div class="items">
              <img v-for="i in 3" :key="i" @click="onChangeItem(`item${i}`)" :src="require(`../assets/images/item${i}.png`)" :alt="`item${i}`">
            </div>
          </div>
          <div class="camera">
              <v-icon class="cameraimg">mdi-camera</v-icon>
              <!-- <router-link to="/apitest"> -->
               <button class="myfacebtn" @click="cameramodal=true"> 내 얼굴로 캐릭터 만들기</button>
              <!-- </router-link> -->
              <v-app class="vapp"></v-app>
              <v-dialog max-width="70%" min-height="50%" v-model="cameramodal">
                <v-card flat tile>
                  <apitest />
                </v-card>
              </v-dialog>
          </div>
        </div>
        <!-- 캐릭터 보여주는 곳 -->
        <div class="rightbox">
          <div class="character">
            <unity src="../unity2/Build/unity2.json" unityLoader="../unity2/Build/UnityLoader.js" ref="myInstance" :hideFooter="true"></unity>
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
import axios from 'axios';
import store from '../vuex/store';
import Apitest from '../views/Apitest.vue'

export default {
  components: {
    Unity,
    Apitest
  },
  data() {
    return {
      // colors: ["black", "red", "yellow", "green", "blue", "purple"],
      colors: [],
      myItems: {
        userId:store.state.kakaoUserInfo.id,
        color:null,
        eye:null,
        eyebrow:null,
        crown:null,
        shield:null,
        sword:null
      },
      // myColor:[]
      items: [],
      cameramodal: false,
    }
  },
  methods: {
    onBack() {
      this.$router.push('/mypage')
    },
    onChangeColor(color) {
      this.$refs.myInstance.message('body', 'ChangeColor', color);
      this.myItems.color = color;
    },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      console.log("캐릭터 정보 불러오기");
      axios.get(`http://localhost:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        if(res.data){
          console.log(res.data);
          this.myItems = res.data;
          console.log(this.myItems);
          this.onChangeColor(this.myItems.color);
          this.onChangeEyebrow(this.myItems.eyebrow);
          this.onChangeEye(this.myItems.eye);
          if(this.myItems.crown){
            this.$refs.myInstance.message('stand', 'ChangeItem', this.myItems.crown)
            // this.onChangeItem(this.myItems.crown)
          }
          if(this.myItems.shield){
            this.$refs.myInstance.message('stand', 'ChangeItem', this.myItems.shield)
            // this.onChangeItem(this.myItems.shield)
          }
          if(this.myItems.sword){
            this.$refs.myInstance.message('stand', 'ChangeItem', this.myItems.sword)
            // this.onChangeItem(this.myItems.sword)
          }
        }
      })
    },
    saveItem(){
      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/product`,this.myItems)
      .then(()=>{
        this.$router.push('/mypage');
      });
    },
    onChangeEyebrow(eyebrow) {
      this.$refs.myInstance.message('stand', 'ChangeEyebrow', eyebrow)
      this.myItems.eyebrow = eyebrow
    },
    onChangeEye(eye) {
      this.$refs.myInstance.message('stand', 'ChangeEye', eye)
      this.myItems.eye = eye
    },
    onChangeItem(item) {
      this.$refs.myInstance.message('stand', 'ChangeItem', item)
      if(item == "item1"){
        if(this.myItems.crown==null){
          this.myItems.crown = item
        }else{
          this.myItems.crown = null
        }
      }else if(item == "item2"){
        if(this.myItems.shield==null){
          this.myItems.shield = item
        }else{
          this.myItems.shield = null
        }
      }else{
        if(this.myItems.sword==null){
          this.myItems.sword = item
        }else{
          this.myItems.sword = null
        }
      }
      console.log(this.myItems)
    }
  },
  created(){
    if(this.$cookies.isKey("access_token")){
      window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              const kakao_account = res.kakao_account;
              axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                // console.log(res);
                this.$store.commit('setKakaoUserInfo', res.data);
              })
          },
      })

      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/product`)
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
    }, 3000);
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
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2);
  /* box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff; */
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
  height: 16vh;
  border: 2px solid white;
  border-radius: 15px;
  margin-bottom: 2vh;
  position: relative;
  background-color: rgba(255,255, 255, 0.2);
}
.mycharacters {
  overflow-x: auto;
  overflow-y: hidden;
  height: 16vh;
  display: flex;
  justify-content: left;
  align-items: center;
  padding: 0 20px;
}
.mycharacters img {
  width: 16%;
  margin-right: 1vw;
  cursor: pointer;
}
.itembox {
  height: 10vh;
  border: 2px solid white;
  border-radius: 15px;
  margin-bottom: 2vh;
  position: relative;
  background-color: rgba(255,255, 255, 0.2);
}
.itemtitle {
  position: absolute;
  /* background-color: white; */
  color: white;
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
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin: auto;
  cursor: pointer;
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
  color: white;
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
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2);
}
.save-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
.myfacebtn {
  color: white;
}
.eyebrowbox {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.eyebrowbox img {
  width: 20%;
  margin: auto;
  cursor: pointer;
}
.noEyebrow {
  margin: auto;
  cursor: pointer;
  font-size: 20px;
  font-family: 'BMJUA';
}
.eyebox {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.eyebox img {
  width: 20%;
  margin: auto;
  cursor: pointer;
}
.items {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.items img {
  width: 20%;
  margin: auto;
  cursor: pointer;
}
</style>