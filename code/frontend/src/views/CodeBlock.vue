<template>
  <div class='wrap'>
    <div class="code-block-container">
      <div class="unity-box">
        <unity class="unity" style="width:100%; height:100%;" src="glacier/Build/glacier.json" unityLoader="glacier/Build/UnityLoader.js" ref="myInstance"></unity>
      </div>
      <div class="code-box" @drop="drop" @dragover="dragover">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
        <div class="block-box">
           <div v-show="isMove" class="block-list">
            <div v-for="(m,index) in moves" :key="index" class="block" :class="'block'+index" draggable="true" @dragstart="dragstartAdd($event)">
              {{m.move_kor}}
            </div>
          </div>
          <div v-show="isObstacle" class="block-list">
            <div class="block">장애물추가</div>
          </div>
        </div>
        <div id="play-box" class="play-box">
          <div v-show="isMove" class="block-list">
            <div style="display: flex; justify-content: center;">
              <div id="play" @click="clickPlayBtn" :style="{'background-color':playClass.background}"><v-icon style="color:white;" size="4vw">mdi-play-circle</v-icon></div>
              
            </div>
            <div id="underplay" style="display: flex; justify-content: center;">
            <div class="block" style="background-color:gray;margin-bottom:0px;" :style="{display:playClass.show}">
              </div>
              </div>
              <div id="block-board">
                <div v-for="(m,index) in resultStep" :index="m.index" :key="index" draggable="true" @dragstart="dragstart(index,$event)" :style="{position:m.position,top: 0,left:0,'margin-left':m.marginleft,'margin-top':m.marginTop}" >
                  <div class="block" :class="'block'+m.num+' '+m.class" v-text="moves[m.num].move_kor" style="margin-bottom:0px;">
                  </div>  
                  <div class="block" style="background-color:gray;margin-bottom:0px;" :style="{display:m.overMe}">
                  </div>
                </div>
            </div>
            <v-btn id="historyBtn" @mouseover="openHistory" @mouseout="closeHistory">
              <v-icon>mdi-history</v-icon>
              <h3>히스토리</h3>
            </v-btn>
            <div id="history" :style="{display:this.showhistory}">
              <div v-for="(step,index) in history" :key="index" >
                {{step.move_kor}}
              </div>
              <div v-if="history.length==0">히스토리 내역이 없습니다.</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ClearModal v-if="isClear" @close="isClear= false" @restart="reStart" @next="nextLevel"/>
    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import ClearModal from '../components/ClearModal.vue';
import FailModal from '../components/FailModal.vue';
import { mapMutations } from 'vuex';

export default {
  name: 'CodeBlock',
  data() {
    return {
      isClear: false,
      isFail: false,
      stageNum: 1,
      isMove: true,
      isObstacle: false,
      distX: '',
      distY: '',
      moves:[
        {
          num:0,
          move:'Up',
          move_kor:'위로 가기'
        },
        {
          num:1,
          move:'Right',
          move_kor:'오른쪽으로 가기'
        },
        {
          num:2,
          move:'Left',
          move_kor:'왼쪽으로 가기'
        },
        {
          num:3,
          move:'Down',
          move_kor:'뒤로 가기'
        },
        {
          num:4,
          move:'TurnRight',
          move_kor:'오른쪽으로 90˚ 회전'
        },
        {
          num:5,
          move:'TurnLeft',
          move_kor:'왼쪽으로 90˚ 회전'
        },
        {
          num:6,
          move:'Jump',
          move_kor:'점프 하기'
        }
      ],
      resultStep:[
      ],
      selectnum:0,
      isAdded: false,
      targetdivNum:0,
      mouseXposition:0,
      mouseYposition:0,
      isOnMove:false,
      targetdiv:'',
      resultmoves:[],
      playClass:{background:'#1dc360',show:'none'},
      playson:0,
      alreadyOverPlay:false,
      history:[],
      showhistory:'none'
    }
  },
  components: {
    Unity,
    ClearModal,
    FailModal,
  },
  computed: {
  },
  created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('clear', this.handleClear)
    window.addEventListener('fail', this.handleFail)
  },
  mounted() {
    this.onMove();
  },
  watch: {
  },
  methods: {
     ...mapMutations(['setInStageNum', 'setInStageStar']),
    clickPlayBtn(){
      this.resultmoves.forEach( step => {
      this.$refs.myInstance.message('JavascriptHook',step.move);
     });
     for(var i=0; i<this.resultmoves.length;i++){
       this.history.push(this.resultmoves[i]);
     }

     this.resultmoves = [];
     this.resultStep = [];
     this.playClass.background='#1dc360';
     this.$refs.myInstance.message('JavascriptHook',"Go");
    },
    openHistory(){
      this.showhistory = 'block';
    },
    closeHistory(){
      this.showhistory = 'none';
    },
    getNeighbor(event){
      var x = this.distX+event.pageX;
      var y = this.distY+event.pageY;
      var playtarget = document.getElementById('play');
      const playRect = playtarget.getBoundingClientRect();

        // console.log(playRect.left+this.distX+" "+(playRect.right+this.distX));
        // console.log(playRect.top+this.distY+" "+(playRect.bottom+this.distY));
        // console.log(x+" "+y);
      if(x<playRect.right+this.distX&&x>playRect.left+this.distX&&y<playRect.bottom+this.distY&&playRect.top+this.distY){
        this.playClass={background:'green',show:'block'};
      }else{
        this.playClass={background:'#1dc360',show:'none'};
      }

     this.resultStep.forEach( step => {
      var stepx = step.x;
      var stepy = step.y;
       if(step.index!=this.targetdivNum&&(x<stepx+30&&x>stepx-30&&y<stepy+30&&y>stepy-30)){
         step.class = 'overMe';
         step.overMe = 'block';
       }else{
         step.class = '';
         step.overMe = 'none';
       }
     });
 
    },
    deleteNode(event){
      event.target.deleteNode();
    },
    onMove() {
      this.isMove = true; this.isObstacle = false
      const MOVE = document.querySelector('.move-menu');
      const OBSTACLE = document.querySelector('.obstacle-menu');
      MOVE.classList.add('on-menu-bar');
      OBSTACLE.classList.remove('on-menu-bar');
    },
    onObstacle() {
      this.isObstacle = true; this.isMove = false
      const MOVE = document.querySelector('.move-menu');
      const OBSTACLE = document.querySelector('.obstacle-menu');
      MOVE.classList.remove('on-menu-bar');
      OBSTACLE.classList.add('on-menu-bar');
    },
    dragstartAdd(event){
      let posX = event.pageX;
      let posY = event.pageY;
      // console.log(event.target);
      this.targetdiv = event.target;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.selectnum = event.target.className;
      this.isAdded = false;
      this.isOnMove = true;//움직이고있다.
    },
    dragstart(mynum,event) {
      this.targetdivNum = mynum;
      // console.log(event.target);
      this.targetdiv = event.target;
      // event.target.style.position = 'absolute';
      let posX = event.pageX;
      let posY = event.pageY;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.isAdded = true;
      this.isOnMove = true; //움직이고있다.
    },
    dragover(event) {
      event.stopPropagation();
      event.preventDefault();
      //마우스가 움직이면서 계속 마우스 위치를 가져온다.
      this.getNeighbor(event);
      },
    updateLink(){
      var parent = this.targetdivNum;
        var son = this.resultStep[parent].son;
        while(son != -1){
          console.log(son+"의 x를 "+this.resultStep[parent].x+"로 바꿈");
          this.resultStep[son].x = Number(this.resultStep[parent].x);
          this.resultStep[son].y = Number(this.resultStep[parent].y)+47;
          parent = son;
          son = this.resultStep[son].son;
        }
    },
    drop(event) {
      const target = document.getElementById('play-box');
      const clientRect = target.getBoundingClientRect(); // DomRect 구하기 (각종 좌표값이 들어있는 객체)
      const relativeLeft = clientRect.left;
      const relativeRight = clientRect.right;
      

      event.stopPropagation();
      event.preventDefault();
      let posX = event.pageX;
      let posY = event.pageY;
      this.onMove = false ; //블록움직임이 끝났다.
      if (posX >= relativeLeft && posX <= relativeRight) {
        if(!this.isAdded){
        var selectedNum = this.selectnum;
        selectedNum = selectedNum.split("block")[2].split(' ')[0]
        this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 'px',marginTop:posY + this.distY + 'px',class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1});
        // const tempTarget = document.getElementById('block-board');
        // this.targetdiv = tempTarget.querySelector(':last-child');
        // console.log(tempTarget.childNodes.findIndex(0));
        this.targetdivNum = this.resultStep.length-1;
        this.updateLink();
        }else{
          this.resultStep[this.targetdivNum].marginleft = posX + this.distX + 'px';
          this.resultStep[this.targetdivNum].marginTop = posY + this.distY + 'px';
          this.resultStep[this.targetdivNum].x = posX + this.distX;
          this.resultStep[this.targetdivNum].y = posY + this.distY;
          this.updateLink();
        }
        // console.log(event);
        if(this.playClass.show=='block'){
          this.playson = this.targetdivNum;
          console.log(this.targetdivNum);
          var tempson = this.playson;
          while(tempson != -1){
              this.resultmoves.push(this.moves[this.resultStep[tempson].num]);
              tempson = this.resultStep[tempson].son;
              this.alreadyOverPlay = true;
            }
          document.getElementById('underplay').appendChild(this.targetdiv);
          this.resultStep[this.targetdivNum].position = 'unset';
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          console.log(this.resultStep);
        }

        var content = window.document.getElementsByClassName("overMe");
        if(content.length!=0){
          console.log(content[0])
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '45px';
          this.resultStep[this.targetdivNum].position = 'unset';
          // this.resultStep[this.targetdivNum].x = content[0].getBoundingClientRect().left+this.distX+50;
          // this.resultStep[this.targetdivNum].y = content[0].getBoundingClientRect().top+45;
          console.log(this.resultStep);
          if(this.isAdded){
            content[0].nextSibling.after(this.targetdiv);
          }
        }
        this.resultStep.forEach( step => {
          if(step.overMe=='block'){
            console.log();
            this.resultStep[step.index].son = this.targetdivNum;
            var parent = step.index;
            var son = this.targetdivNum;
            while(son != -1){
              console.log(son+"의 x를 "+this.resultStep[parent].x+"로 바꿈");
              this.resultStep[son].x = Number(this.resultStep[parent].x);
              this.resultStep[son].y = this.resultStep[parent].y+47;
              parent = son;
              son = this.resultStep[son].son;
            }
            step.overMe = 'none'
          }
     });
     this.playClass.show='none';
      }
    },
    LevelLoad() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'Stage', this.stageNum)
    },
    reStart() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    nextLevel() {
      this.commandList = []
      this.stageNum += 1
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    handleStart() {
      setTimeout(() => {
        this.LevelLoad();
      }, 10);
    },
    handleClear() {
      this.onModal();
      this.history.push({move:'clear',move_kor:"스테이지"+this.stageNum+' 성공!',num:-1})
    },
    handleFail() {
      this.onModal2();
      this.history.push({move:'fail',move_kor:"스테이지"+this.stageNum+' 실패!',num:-2})
    },
    onModal() {
      this.isClear = true;
      this.setInStageNum(this.stageNum);
      this.setInStageStar(3);
    },
    onModal2() {
      this.isFail = true;
    },
  },
  beforeDestroy () {
    window.removeEventListener('start', this.handleStart)
    window.removeEventListener('clear', this.handleClear)
    window.removeEventListener('fail', this.handleFail)
  },
}
</script>

<style scoped>
.wrap{
  padding:0px;
  width:100%;
  height:100%;
}
.code-block-container {
  display: flex;
  /* margin-top: 100px; */
  width: 100%;
  height:100%;
}

.code-block-container .unity-box {
  width: 60%;
  /* margin-right: 1%; */
  height: 100%;
  /* background-color: grey; */
}

.code-block-container .code-box {
  width: 40%;
  height: 100%;
  /* background-color: bisque; */
  display: flex;
  position: relative;
}

.code-box .block-box {
  width: 30%;
  /* background-color: blue; */
  display: flex;
  height:100%
}

.code-box .play-box {
  width: 70%;
  height:100%;
  /* background-color: brown; */
  border: 1px solid #a4d4ff;
  /* position: relative; */
  /* background-image: url('../assets/images/tile.png');
      background-repeat: repeat; */
}

.block-menu-bar {
  margin-left:-100px;
  width: 100px;
}

.block-box .block-list {
  width: 100%;
  padding: 10px;
  /* background-color: #0F4C81; */
  border: 1px solid #a4d4ff;
  overflow-y: scroll;
}

.block-menu-bar .on-menu-bar {
  background-color: #a4d4ff;
  font-weight: 700;
}

.block-menu-bar .menu {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-left: 5px;
  height: 50px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  /* background-color: chocolate; */
  border: 1px solid #a4d4ff;
  margin-bottom: 5px;
  cursor: pointer;
  transition: background-color .5s ease;
}

.block {
  width:105px;
  height:45px;
  text-align: center;
  padding: 5px;
  border-radius: 20px;
  background-color: #0F4C81;
  margin-bottom: 10px;
  cursor: pointer;
  color: #fff;
  font-size: 14px;
  float: left;
  clear: both;
}

.play-box .play {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 50px;
  background-color: bisque;
}

.code-block-container .unity-box .unity {
  width: 100%;
  height: 100%;
}

.overMe{
  background-color: red;
}

#play{
  top:0;
  left: 3px;
  width:100px;
  height:5vw;
  border-radius: 5vw;
  min-width: 50px;
  min-height:50px;
  display:flex;
  justify-content: center;
}

#historyBtn{
  position:absolute;
  bottom:0;
  right:0;
  margin:5px;
}

#history{
    position: absolute;
    width: 80%;
    height: 80%;
    bottom: 10%;
    right: 10%;
    z-index: 2;
    background-image: url('../assets/images/history.png');
    background-color: transparent;
    background-size: 100% 100%;
    padding: 107px 50px;
}
</style>