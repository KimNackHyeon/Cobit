<template>
  <div class="wrap">
    <div class="map-container">
      <div class="map-header">
        <div class="back-btn" @click="goMypage"><i class="fas fa-chevron-left"></i>돌아가기</div>
      </div>
      <div class="map-body">
        <div id="stage1story" @click="clickStory(1)" :style="{display:openStory1}">
          <div>
          <div class="story story" >
            <div style="width:20%; height:100%;">
              <img src="../assets/images/stage1_no_text.png" style="width:100%; height:100%; border-radius: 30px 0px 0px 30px;">
            </div>
            <div style="width:80%; height: 100%; display:flex; justify-content: center; align-items: center; font-size: 1.6vw; font-weight: 600;" v-html="story.first">
            </div>
          </div>
          <div style="width:100%;  display: flex; justify-content: center;">
            <div class="goal_title" style="margin: 6vh; background-color: #ffcf00; box-shadow: 1px 1px 7px #00000057;"><div class="goal_title" style="box-shadow: 1px 1px 4px #00000075 inset; width: 180px; height: 63px; font-size: x-large; font-weight: bold;">학습목표</div></div>
          </div>
          
          <div class="story goal" v-html="goal.first">
          </div>
          </div>
        </div>
        <div id="stage1story" @click="clickStory(2)" :style="{display:openStory2}">
          <div>
          <div class="story story" >
            <div style="width:20%; height:100%;">
              <img src="../assets/images/stage2.png" style="width:100%; height:100%; border-radius: 30px 0px 0px 30px;">
            </div>
            <div style="width:80%; height: 100%; display:flex; justify-content: center; align-items: center; font-size: 1.6vw; font-weight: 600;" v-html="story.second">
            </div>
          </div>
          <div style="width:100%;  display: flex; justify-content: center;">
            <div class="goal_title" style="margin: 6vh; background-color: #ffcf00; box-shadow: 1px 1px 7px #00000057;"><div class="goal_title" style="box-shadow: 1px 1px 4px #00000075 inset; width: 180px; height: 63px; font-size: x-large; font-weight: bold;">학습목표</div></div>
          </div>
          
          <div class="story goal" v-html="goal.second">
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
          <div v-if="!inform.open&&!inform.user" class="stage-area lock-stage-area"></div>
          <div v-if="!inform.open&&!inform.user" class="stage-star lock-stage-star">
            <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>
          </div>
          <div class="map-road-box"></div>
          <div v-if="!inform.open&&inform.user" class="stage-area lock-stage-area"></div>
          <div v-if="!inform.open&&inform.user" class="stage-star">
            <i v-for="(star,idx) in inform.star" :key="`star+${index}+${idx}`" class="fas fa-star star"></i>
            <i v-for="(unstar,idx) in inform.unstar" :key="`unstar+${index}+${idx}`" class="fas fa-star unstar"></i>
          </div>
          <div v-if="!inform.open&&inform.user" class="map-character" @click="onModal(inform, index+1)">
            <img src="../assets/images/penguin2.png" alt="">
          </div>
          <div v-if="index != mapInform.length - 1" class="map-road-box"></div>
          <div v-if="isLast">
            <div v-if="index == mapInform.length - 1" class="map-road-box map-road-last"></div>
          </div>
          <div v-if="!isLast">
            <div v-if="index == mapInform.length - 1" class="map-road-box map-road-last map-road-last-un"></div>
          </div>
        </div>
        <div v-if="isLast" class="map-stage-box map-last-box" @click="goNext">
          <div class="stage-area"></div>
        </div>
        <div v-if="!isLast" class="map-stage-box map-last-box-un" @click="goNext">
          <div class="stage-area lock-stage-area lock-stage-area-un"></div>
        </div>
      </div>
      <div class="map-footer">
        <i class="fas fa-star"></i>
        <div class="starbar">
          <div class="mystar"></div>
          <div class="startotal"></div>
          <div class="starnum"><span>{{starCount}} / {{totalCount}}</span></div>
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
      story: { 
        first: "Cobit 마을의 컴퓨터에 원인 모를 문제가 생겼다! <br> 문제가 바이러스라고? <br> 지피지기면 백전백승! 바이러스에 대해 알아보는 모험을 떠나보자!",
        second: "중급 스토리"
      },
      goal: {
        first : "<ul><li>블록 명령어를 순서대로 사용하여 미션을 해결할 수 있다.</li> <br><li>프로그래밍의 원리인 순차에 따라 명령을 수행할 수 있다.</li><br><li>조심해야할 컴퓨터 바이러스에 대해 알 수 있다.</li></ul>",
        second: "중급 목표"
      },
      showModal: false,
      showModal2: false,
      mapInform: [],
      mapStar: {
        first: 33.3,
        second: 33.3,
        third: 0,
      },
      type: null,
      openStory1: '',
      openStory2: '',
      starCount : 0,
      clearLength: 0,
      isLast: false,
      totalCount : 0,
    }
  },
  components: {
    StageModal,
    DifficultyModal,
  },
  computed: {
  },
  async created() {
    // window.addEventListener('scroll', this.handleScroll)
    this.type = this.$cookies.get('stageType');
    if (this.type == 1) {
      this.openStory1 = 'flex'
      this.openStory2 = 'none'
    } else if (this.type == 2) {
      this.openStory1 = 'none'
      this.openStory2 = 'flex'
    }
    if(this.$cookies.isKey("access_token")){
      this.loadStage();
      let kakao_account;
      await window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              kakao_account = res.kakao_account;
          },
      });
      await axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                this.$store.commit('setKakaoUserInfo', res.data);
                
              });
      await this.loadMyStage();
    }
  },
  mounted() {
  },
  watch: {
     starCount(){
      if(this.starCount == 0) {
      $(".startotal").css("border-radius", "15px")
      $(".startotal").css("width", "100%")
      }
      else if(this.starCount == 15){
        $(".mystar").css("border-radius", "15px")
        $(".mystar").css("width", "100%")
      }
      else {
        const starratio = (this.starCount / this.totalCount) * 100
        $(".mystar").css("width", `${starratio}%`)
        $(".startotal").css("width", `${100 - starratio}%`)
      }
    }
  },
  methods: {
    ...mapMutations(['setStageDetail', 'setStageNum','setStageType']),
    clickStory(type){
      if (type == 1) {
        this.openStory1 = 'none';
      } else if (type == 2) {
        this.openStory2 = 'none'
      }
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
      axios.get(`http://localhost:9999/cobit/stage/user`,{
        params:{
          id : store.state.kakaoUserInfo.id,
          type : this.type
        }
      })
      .then(res => {
        console.log(res)
        this.clearLength = res.data.length
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
        this.totalCount = this.mapInform.length*3;
      })
    },
    setLast() {
      if (this.type == 1 && this.clearLength == 5) {
        this.isLast = true
      } else if (this.type == 2 && this.clearLength == 3) {
        this.isLast = true
      } else {
        this.isLast = false
      }
    },
    goNext() {
      this.$cookies.set('stageType', parseInt(this.type)+1);
      this.$router.go(0);
    }
  },
  
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.starbar {
  display: flex;
  justify-content: center;
  align-content: center;
  width: 95%;
  margin-left: 2%;
  /* background: white; */
  border-radius: 15px;
  /* text-align: center; */
  font-size: 25px;
  font-family: 'BMJUA';
  font-weight: 600;
  /* margin: 1%; */
  /* padding: 1%; */
  color: black;
  position: relative;
}
.mystar {
  height: 7vh;
  display: inline-block;
  background: #a4d4ff;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
}
.startotal {
  height: 7vh;
  display: inline-block;
  /* background-color: white; */
  background-color: #eee;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}
.starnum {
  display: inline-block;
  position: absolute;
  top: 50%;
  left: 45%;
  transform: translate(0, -50%);
}
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
  padding-right: 150px;
}

.map-body .map-last-box {
  padding: 0;
  margin-left: 60px;
  position: relative;
  cursor: pointer;
}
.map-body .map-last-box::before {
  content: '';
  position: absolute;
  top: -60%;
  left: 40%;
  background-image: url('../assets/images/army.png');
  background-repeat : no-repeat;
  background-size : cover;
  background-size: 100% 100%;
  filter: hue-rotate(180deg);
  width: 70px;
  height: 80px;
  z-index: 2;
  transition: .5s ease;
}
.map-body .map-last-box::after {
  content: '다음단계로';
  width: 150px;
  font-size: 25px;
  text-align: center;
  position: absolute;
  bottom: -30%;
  transition: .3s ease;
}

.map-body .map-last-box:hover::before {
  animation: moving .5s;
}

.map-body .map-last-box:hover::after {
  color: #0088ff;
}

.map-body .map-last-box-un {
  padding: 0;
  margin-left: 60px;
  position: relative;
  cursor: pointer;
}

.map-body .map-last-box-un::before {
  content: '';
  position: absolute;
  top: -50%;
  left: 15%;
  background-image: url('../assets/images/virus.png');
  background-repeat : no-repeat;
  background-size : cover;
  background-size: 100% 100%;
  width: 70px;
  height: 80px;
  z-index: 2;
  transition: .5s ease;
  animation: bounce 3s infinite ease-in-out;
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

.map-body .lock-stage-area-un::before {
  content: '' !important;
}

.map-last-box .stage-area::before {
  content: '' !important;
}

.map-last-box-un .stage-area {
  background: linear-gradient(to bottom, rgb(218, 178, 178) 0%,rgb(179, 101, 101) 47%,rgb(173, 43, 43) 100%) !important;
}

.map-last-box-un .stage-area::before {
  background: linear-gradient(to bottom, rgb(255, 209, 209) 0%,rgb(219, 124, 124) 47%,rgb(209, 59, 59) 100%) !important;
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
  width: 180px;
  height: 10px;
  flex: 0 0 auto;
  background: linear-gradient(to bottom, rgba(228,245,252,1) 0%,rgba(191,232,249,1) 50%,rgba(159,216,239,1) 51%,rgba(42,176,237,1) 100%);
  box-sizing: border-box;
  z-index: -2;
}

.map-body .map-road-last {
  width: 250px;
  right: -70px;
  background: linear-gradient(to bottom, rgba(228,245,252,1) 0%,rgba(191,232,249,1) 50%,rgba(159,216,239,1) 51%,rgba(42,176,237,1) 100%);
}

.map-body .map-road-last-un {
  background: linear-gradient(to right, rgb(211, 211, 211) 0%,rgb(184, 183, 183) 50%,rgb(197, 161, 161) 55%,rgb(197, 53, 53) 100%);

}

.map-body .map-road-last::after {
  content: '';
  position: absolute;
  top: -5px;
  right: 0;
  width: 250px;
  height: 20px;
  flex: 0 0 auto;
  background: linear-gradient(90deg, #fff 50%, rgba(0,0,0,0) 0);
  background-size: 30px 100%;
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
  z-index: 2;
  color: rgb(243, 243, 0);
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
  z-index: 99;
    top: 15px;
    left: 15px;
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
  display:flex; 
  justify-content:center;
  padding-top: 10vh;
}
.story{
  background-color:white; width:80vw; height:30vh; border-radius: 30px;
  box-shadow: 0px 0px 15px #424141b0 inset;
  display: flex;
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
  font-size: 2.2vh;
  font-weight: bold;
    display: flex;
  align-items: center;
  justify-content: center;
}

@keyframes moving {
  0% { top: -60%; }
  30% { top: -90%; }
  40% { top: -90%; }
  100% { top: -60%; }
}

@keyframes bounce {
  0% { top: -50%; }
  50% { top: -70%; }
  100% { top: -50%; }
}

</style>