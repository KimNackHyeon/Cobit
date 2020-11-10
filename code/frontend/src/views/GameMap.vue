<template>
  <div class="wrap">
    <div class="map-container">
      <div class="map-header">
        <div class="back-btn" @click="goMypage"><i class="fas fa-chevron-left"></i>돌아가기</div>
      </div>
      <div class="map-body">
        <div id="stage1story" @click="clickStory" v-if="openStory">
          <div>
          <div class="story story" >
            <div style="width:30%; height:100%;float:left;">
              <img src="../assets/images/stage1_no_text.png" style="width:100%; height:100%; border-radius: 30px 0px 0px 30px;">
            </div>
            <div style="width: 70%; height: 100%; float: left; padding: 0 8vw; font-size: x-large; font-weight: 600;display: flex; align-items: center; justify-content: center;" v-html="story">
            </div>
          </div>
          <div style="width:100%;  display: flex; justify-content: center;">
            <div class="goal_title" style="margin: 6vh; background-color: #ffcf00; box-shadow: 1px 1px 7px #00000057;"><div class="goal_title" style="box-shadow: 1px 1px 4px #00000075 inset; width: 180px; height: 63px; font-size: x-large; font-weight: bold;">학습목표</div></div>
          </div>
          
          <div class="story goal" v-html="goal">
          </div>
          </div>
        </div>
        <div class="map-stage-box" v-for="(inform, index) in mapInform" :key="`map+${index}`">
          <div class="stage-num">{{ index+1 }}</div>
          <div v-if="inform.open" class="stage-area" @click="onModal(inform, index+1)"></div>
          <div v-if="inform.open" class="stage-star">
            <i v-for="(star,idx) in inform.star" :key="`star+${index}+${idx}`" class="fas fa-star star"></i>
            <i v-for="(unstar,idx) in inform.unstar" :key="`unstar+${index}+${idx}`" class="fas fa-star unstar"></i>
          </div>
          <div v-if="!inform.open" class="stage-area lock-stage-area"></div>
          <div v-if="!inform.open" class="stage-star lock-stage-star">
            <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>
          </div>
          <div class="map-road-box"></div>
          <div v-if="inform.user" class="map-character" @click="onModal(inform, index+1)">
            <img src="../assets/images/penguin2.png" alt="">
          </div>
        </div>
      </div>
      <div class="map-footer">
        <i class="fas fa-star"></i>
        <div class="starbar">
          <div class="mystar"></div>
          <div class="startotal"></div>
          <div class="starnum"><span>{{starCount}} / 15</span></div>
        </div>
        <!-- <div class="map-gauge">
          <div class="map-1star-gauge">★ {{ mapStar.first }}%</div>
          <div class="map-2star-gauge">★★ {{ mapStar.second }}%</div>
          <div class="map-3star-gauge">★★★ {{ mapStar.third }}%</div>
        </div> -->
      </div>
      
    </div>

    <StageModal v-if="showModal" @close="showModal= false"/>
    <DifficultyModal v-if="showModal2" @close="showModal2= false"/>
  </div>
</template>

<script>
import StageModal from '../components/StageModal.vue';
import DifficultyModal from '../components/DifficultyModal.vue';
import { mapMutations } from 'vuex';
import axios from 'axios';
import store from '../vuex/store';
import $ from 'jquery';

export default {
  name: 'GameMap',
  data() {
    return {
      story:"Cobit 마을의 컴퓨터에 원인 모를 문제가 생겼다! <br> 문제가 바이러스라고? <br> 지피지기면 백전백승! 바이러스에 대해 알아보는 모험을 떠나보자!",
      goal:"<ul><li>블록 명령어를 순서대로 사용하여 미션을 해결할 수 있다.</li> <br><li>프로그래밍의 원리인 순차에 따라 명령을 수행할 수 있다.</li><br><li>조심해야할 컴퓨터 바이러스에 대해 알 수 있다.</li></ul>",
      showModal: false,
      showModal2: false,
      mapInform: [],
      mapStar: {
        first: 33.3,
        second: 33.3,
        third: 0,
      },
      type: null,
      openStory:true,
      starCount : 0,
    }
  },
  components: {
    StageModal,
    DifficultyModal,
  },
  computed: {
  },
  created() {
    // window.addEventListener('scroll', this.handleScroll)
    this.type = this.$cookies.get('stageType');
    console.log(this.type);
    if(this.$cookies.isKey("access_token")){
      this.loadStage();
      window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              const kakao_account = res.kakao_account;
              axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                this.$store.commit('setKakaoUserInfo', res.data);
                this.loadMyStage();
              })
          },
      })
    }
  },
  mounted() {
    // this.getStarRatio();
    // 별의 갯수에 따라 width 설정
    if(this.starCount == 0) {
      console.log('실행')
      $(".startotal").css("border-radius", "15px")
      $(".startotal").css("width", "100%")
    }
    else if(this.starCount == 90){
      $(".mystar").css("border-radius", "15px")
      $(".mystar").css("width", "100%")
    }
    else {
      const starratio = (this.starCount / 90) * 100
      $(".mystar").css("width", `${starratio}%`)
      $(".startotal").css("width", `${100 - starratio}%`)
    }
  },
  watch: {
  },
  methods: {
    ...mapMutations(['setStageDetail', 'setStageNum','setStageType']),
    clickStory(){
      this.openStory = false;
    },
    // getStarRatio() {
      // this.mapStar.third = (100 - this.mapStar.first - this.mapStar.second).toString().substring(0,4);
      // const FIRST = document.querySelector('.map-1star-gauge');
      // const SECOND = document.querySelector('.map-2star-gauge');
      // const THIRD = document.querySelector('.map-3star-gauge');

      // FIRST.style.width = this.mapStar.first + '%';
      // SECOND.style.width = this.mapStar.second + '%';
      // THIRD.style.width = this.mapStar.third + '%';
    // },
    onModal(detail, num) {
      this.setStageDetail(detail);
      this.setStageNum(num);
      this.setStageType(this.type);
      this.showModal = true;
    },
    onModal2() {
      this.showModal2 = true;
    },
    goMypage(){
      this.$router.push('/mypage');
    },
    loadMyStage(){
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/stage/user?id=${store.state.kakaoUserInfo.id}`)
      .then(res => {
        // console.log(res);
        var index = 0;
        res.data.forEach(d => {
          const map = {
            open: true,
            star: d.star,
            unstar: 3 - d.star,
            user: false,
            content : this.mapInform[d.stageId-1].content,
          }
          this.starCount += d.star;
          this.$set(this.mapInform, d.stageId-1, map)
          // this.mapInform[d.stageId-1] = map;
          index++;
        });
        this.$set(this.mapInform, index, {
          open : false,
          star : 0,
          unstar : 3,
          user : true,
          content : this.mapInform[index].content,
        })
      })
    },
    loadStage(){
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/stage?type=${this.type}`)
      .then(res => {
        res.data.forEach(map =>{
          this.mapInform.push( {
              open: false,
              star: 0,
              unstar: 3,
              user: false,
              content : map.content,
            });
        });
      })
    },
  },
  
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.starbar {
  display: inline-block;
  width: 88%;
  /* background: white; */
  border-radius: 15px;
  /* text-align: center; */
  font-size: 25px;
  font-weight: 600;
  /* margin: 1%; */
  /* padding: 1%; */
  color: black;
  position: relative;
}
.mystar {
  height: 5vh;
  display: inline-block;
  background: #a4d4ff;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
}
.startotal {
  height: 5vh;
  display: inline-block;
  background-color: white;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}
.starnum {
  display: inline-block;
  position: absolute;
  top: 5px;
  left: 45%;
}
/* ///////////////////////////////// */
.wrap {
  height: 100%;
}
.map-container {
  margin-top: 50px;
  width: 100%;
  height: 70%;
  /* height: calc(100vh - 100px); */
  /* background-color: bisque; */
}

.map-header .back-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 180px;
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

.map-header .back-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

.map-header .back-btn .fa-chevron-left {
  margin: 0;
  margin-right: 10px;
}

.map-body {
  height: 70%;
  margin: 20px 0;
  overflow-x: auto;
  overflow-y: hidden;
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
}

.map-body::-webkit-scrollbar { height: 10px;}
::-webkit-scrollbar-track { background-color: #ffffff; }
::-webkit-scrollbar-thumb { background: #cee8ff; }
::-webkit-scrollbar-button { display: none; }
::-webkit-scrollbar-thumb:hover { background: #a4d5ff; }
::-webkit-scrollbar-thumb:active {background: #84c5ff; }

.map-body .map-stage-box {
  /* height: 40%;
  min-height: 280px; */
  /* border: 1px solid aqua; */
  flex: 0 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: BMJUA;
  font-size: 30px;
  position: relative;
  padding-right: 100px;
}

.map-stage-box .stage-area {
  position: relative;
	overflow: hidden;
  margin: 0 auto;
	width: 100px;
	height: 60px;
	border-radius: 50px/25px;
  background: linear-gradient(to bottom, #e4eef3 0%,#c0dff1 47%,#94c7e7 100%);
  margin: 10px 0;
  cursor: pointer;
}

.map-stage-box .stage-area::before {
	position: absolute;
	left: 0;
	top: 0;
	width: 100px;
	height: 50px;
	border-radius: 50px/25px;
  background: linear-gradient(to bottom, #f0f9ff 0%,#cbebff 47%,#a1dbff 100%);
  font-family: "Font Awesome 5 Free";
  content: '\f005';
  font-weight: 900;
  color: yellow;
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.map-body .lock-stage-area {
  background: grey !important;
  cursor: default !important;
}

.map-body .lock-stage-area::before {
  background: rgb(158, 158, 158) !important;
  content: '\f023' !important;
  color: #000 !important;
  text-shadow: none !important;
}

.map-stage-box .stage-star {
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* .stage-star > span {
  margin: 0 2px;
  font-size: 25px;
  color: yellow;
} */

/* .stage-star > span:nth-child(2) {
  margin-top: 10px;
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;

} */

.stage-star .fa-star {
  font-size: 20px;
  margin: 0 2px;
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
}

.stage-star .star {
  color: rgb(243, 243, 0);
}

.stage-star .unstar {
  color: #fff;
}

.stage-star i:nth-child(2) {
  margin-top: 5px;
}

.lock-stage-star i {
  color: transparent !important;
  text-shadow: none !important;
}

.map-body .map-road-box {
  position: absolute;
  top: 50%;
  right: -10px;
  transform: translate(0, -50%);
  width: 120px;
  height: 10px;
  flex: 0 0 auto;
  background: linear-gradient(to bottom, rgba(228,245,252,1) 0%,rgba(191,232,249,1) 50%,rgba(159,216,239,1) 51%,rgba(42,176,237,1) 100%);
  box-sizing: border-box;
  z-index: -1;
}

.map-body .map-character{
  position: absolute;
  top: -10px;
  left: 10px;
  width: 80px;
  height: 90px;
  cursor: pointer;
}

.map-body .map-character>img {
  width: 100%;
  height: 100%;
}

.map-footer {
  width: 100%;
  height: 20%;
  display: flex;
  align-items: center;
  position: relative;
}

.map-footer .fa-star {
  position: absolute;
  font-size: 60px;
  color: rgb(243, 243, 0);
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
}

.map-footer .map-gauge {
  width: 100%;
  height: 35%;
  margin-left: 25px;
  border: 2px solid black;
  border-radius: 30px;
  display: flex;
  box-sizing: content-box;
  font-family: BMJUA;
}

.map-gauge > div {
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 700;
  font-size: 20px;
}

.map-gauge .map-1star-gauge {
  background-color: grey;
}

.map-gauge .map-2star-gauge {
  background-color: rgb(253, 253, 105);
}

.map-gauge .map-3star-gauge {
  background-color: #0088ff;
  border-bottom-right-radius: 30px;
  border-top-right-radius: 30px;
}

@media(max-width: 1280px) {
  /* .map-header .back-btn {
    width: 26vw;
    height: 8.5vw;
    font-size: 4vw;
  }
  .map-body .map-stage-box {
    font-size: 5vw;
  }
  .map-stage-box .stage-area {
    width: 18vw;
    height: 9vw;
    border-radius: 11vw/4.5vw;
    margin: 1.5vw 0;
  }
  .map-stage-box .stage-area::before {
    width: 18vw;
    height: 7vw;
    border-radius: 10vw/3.5vw;
    text-shadow: -.2vw 0 #000,
                0 .2vw #000,
                .2vw 0 #000,
                0 -.2vw #000;
    font-size: 2.5vw;
  }
  .stage-star .fa-star {
    font-size: 3vw;
    margin: 0 .7vw;
  }

  .stage-star .fas {
    text-shadow: -.2vw 0 #000,
                0 .2vw #000,
                .2vw 0 #000,
                0 -.2vw #000;
  }
  .map-body .map-road-box {
    width: calc(200px - 1vw);
    height: 2vw;
    top: 11vw;
  }
  .map-body .map-character {
    width: 12vw;
    height: 12vw;
    top: 1vh;
    left: 3vw;
  }
  .map-footer .fa-star {
    font-size: 10vw;
    text-shadow: -.2vw 0 #000,
                0 .2vw #000,
                .2vw 0 #000,
                0 -.2vw #000;
  }
  .map-footer .map-gauge {
    height: 5vw;
    margin-left: 4vw;
  }
  .map-gauge > div {
    font-size: 3vw;
  }
  .map-gauge .map-1star-gauge {
    margin-left: 2vw;
  } */
}
#stage1story{
      background-color: #000000a1;
    position: absolute;
    width: 100vw;
    height: 100vh;
    right: 0;
    top: 0;
    z-index: 3;
         display:flex; justify-content:center;
      padding-top: 10vh;
}
.story{
  background-color:white; width:80vw; height:30vh; border-radius: 30px;
    box-shadow: 0px 0px 15px #424141b0 inset;
}
.goal_title{
    width: 200px;
    height: 80px;
    position: absolute;
    background: white;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    border-radius: 40px;
}

.goal{
    width: 50vw;
    height: 40vh;
    margin: 10vh 15vw;
    background-color: #fafff7;
    padding: 5vh 5vw;
    font-size: 3vh;
    font-weight: bold;
     display: flex;
    align-items: center;
    justify-content: center;
}
</style>