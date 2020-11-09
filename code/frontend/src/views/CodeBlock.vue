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
            <div style="display:flex; justify-content:center; margin-left: 30%;">
              <div id="play" @click="clickPlayBtn" :style="{'background-color':playClass.background}"><v-icon style="color:white;" size="4vw">mdi-play-circle</v-icon></div>
              
            </div>
            <div style="display:flex; justify-content:center;margin-left: 30%;">
                <div id="underplay" >
                  <div class="block" style="background-color:gray;margin-bottom:0px;" :style="{display:playClass.show}">
                    </div>
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
            <v-btn id="hintBtn" @click="clickHint" @mouseover="openHint" @mouseout="closeHint">
              <v-icon>mdi-lightbulb-on</v-icon>
              <h3>힌트</h3>
            </v-btn>
            <div id="hint" :style="{display:this.showhint}">
                <div>{{hint}}</div>
                <div v-if="hint==''">해당 스테이지의 힌트가 없습니다.</div>
            </div>
            <v-btn id="historyBtn" @click="clickHistory" @mouseover="openHistory" @mouseout="closeHistory">
              <v-icon>mdi-history</v-icon>
              <h3>히스토리</h3>
            </v-btn>
            <div id="history" :style="{display:this.showhistory}">
              <div style="width:100%; height:100%; overflow-y:auto;">
                <div v-for="(step,index) in history" :key="index" >
                  {{step.move_kor}}
                </div>
                <div v-if="history.length==0">히스토리 내역이 없습니다.</div>
              </div>
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
          move_kor:'밑으로 가기'
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
        },
      ],
      defaultStep:[
        {
          num:0,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'10px',
          index:0,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:1,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'65px',
          index:1,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:2,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'120px',
          index:2,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:3,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'175px',
          index:3,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:4,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'230px',
          index:4,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:5,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'285px',
          index:5,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:6,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'340px',
          index:6,x:0,y:0,son:-1,onPlayBtn:false
        },
      ],
      resultStep:[
        {
          num:0,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'10px',
          index:0,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:1,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'65px',
          index:1,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:2,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'120px',
          index:2,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:3,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'175px',
          index:3,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:4,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'230px',
          index:4,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:5,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'285px',
          index:5,x:0,y:0,son:-1,onPlayBtn:false
        },
        {
          num:6,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'340px',
          index:6,x:0,y:0,son:-1,onPlayBtn:false
        },
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
      playson:-1,
      alreadyOverPlay:false,
      history:[],
      showhistory:'none',
      clickhistory:false,
      showhint:'none',
      clickhint:false,
      hint:"스테이지의 힌트"
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
      var tempson = this.playson;
      var delNode = [];
      while(tempson != -1){
        this.resultmoves.push(this.moves[this.resultStep[tempson].num]);
        delNode.push(this.resultStep[tempson].index);
        tempson = this.resultStep[tempson].son;
      }
      console.log("resultStep["+0+"]="+this.resultStep[0].son);
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
    //  console.log(delNode)
    //  for(var j=0; j<delNode.length;j++){
    //    this.resultStep.pop(j);
    //  }
      this.playson = -1;
      this.alreadyOverPlay = false;
    },
    clickHint(){
      if(this.clickhint){
        this.showhint = 'none';
        this.clickhint = false;
      }else{
        this.showhint = 'block';
        this.clickhint = true;
      }
    },
    openHint(){
        this.showhint = 'block';
    },
    closeHint(){
      this.showhint = 'none';
      this.clickhint = false;
    },
    clickHistory(){
      if(this.clickhistory){
        this.showhistory = 'none';
        this.clickhistory = false;
      }else{
        this.showhistory = 'block';
        this.clickhistory = true;
      }
    },
    openHistory(){
      this.showhistory = 'block';
    },
    closeHistory(){
      if(!this.clickhistory){
        this.showhistory = 'none';
      }
    },
    closeclickHistory(){
      this.showhistory = 'none';
      this.clickhistory = false;
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
      var checkchilds = [];
       if(this.resultStep[this.targetdivNum].son!=-1){
         var checkson = this.resultStep[this.targetdivNum].son;
         while(checkson!=-1){
           checkchilds.push(checkson);
           checkson = this.resultStep[checkson].son;
         }
       }
     this.resultStep.forEach( step => {
      var stepx = step.x;
      var stepy = step.y;
       if(this.resultmoves.includes(step)){
          if(step.index!=this.targetdivNum&&(x<stepx+20&&x>stepx-20&&y<stepy+65&&y>stepy+30)){
            step.class = 'overMe';
            step.overMe = 'block';
          }else{
            step.class = '';
            step.overMe = 'none';
          }
        }
        else{
          if(step.index!=this.targetdivNum&&!checkchilds.includes(step.index)&&(x<stepx+20&&x>stepx-20&&y<stepy+25&&y>stepy-10)){
            step.class = 'overMe';
            step.overMe = 'block';
          }else{
            step.class = '';
            step.overMe = 'none';
          }
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
      event.dataTransfer.effectAllowed = 'copyMove';
      event.dataTransfer.dropEffect = "copy";
      this.targetdiv = event.target;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.selectnum = event.target.className;
      this.isAdded = false;
      this.isOnMove = true;//움직이고있다.
      var selectedNum = this.selectnum;
      selectedNum = selectedNum.split("block")[2].split(' ')[0]
      this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 'px',marginTop:posY + this.distY + 'px',class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false});
      // this.alterDragObject();
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
        // var selectedNum = this.selectnum;
        // selectedNum = selectedNum.split("block")[2].split(' ')[0]
        // this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 'px',marginTop:posY + this.distY + 'px',class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false});
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
          if(!this.alreadyOverPlay){
            this.playson = this.targetdivNum;
            document.getElementById('underplay').appendChild(this.targetdiv);
          }else{
            var originalPlayson = this.playson;
            this.resultStep[this.targetdivNum].son = originalPlayson;
            this.playson = this.targetdivNum;
            document.getElementById('underplay').prepend(this.targetdiv);
          }
          this.resultStep[this.targetdivNum].position = 'unset';
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          if(!this.alreadyOverPlay){
              this.alreadyOverPlay = true;
            }
        }

        var content = window.document.getElementsByClassName("overMe");
        if(content.length!=0){
          if(this.isAdded){
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          this.resultStep[this.targetdivNum].position = 'unset';
          // this.resultStep[this.targetdivNum].x = content[0].getBoundingClientRect().left+this.distX+50;
          // this.resultStep[this.targetdivNum].y = content[0].getBoundingClientRect().top+45;
          // console.log(this.resultStep);
            content[0].nextSibling.after(this.targetdiv);
          }
        }
        // var original_son = -1;
        this.resultStep.some( step => {
          if(step.overMe=='block' || step.class=='overMe'){
            var os = this.resultStep[step.index].son;
            this.resultStep[step.index].son = this.targetdivNum;
            // console.log("원래"+step.index+"의 son "+this.resultmoves[step.index].son+"을 "+this.targetdivNum+"로 바꿈");
            this.resultStep[this.targetdivNum].son = os;
            // console.log(this.targetdivNum+"의 son을"+os+"로 바꿈");
            var parent = step.index;
            var son = this.targetdivNum;
            while(son != -1){
              this.resultStep[son].x = Number(this.resultStep[parent].x);
              this.resultStep[son].y = this.resultStep[parent].y+47;
              parent = son;
              son = this.resultStep[son].son;
            }
            step.overMe = 'none';
          }
          if(step.overMe=='block' || step.class=='overMe')return;
     });
    //  this.resultStep[this.targetdivNum].son = original_son;
    console.log(this.resultStep);
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
    if(!this.isAdded){
      var temp = document.getElementById('block-board').childNodes;
      console.log(temp[temp.length-1])
      
    }
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
  position:absolute;
  width: 30%;
  /* background-color: blue; */
  display: flex;
  height:100%
}

.code-box .play-box {
  width: 100%;
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

#hintBtn{
  position:absolute;
  bottom:50px;
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

#hint{
  position: absolute;
    width: 60%;
    height: 30%;
    bottom: 10%;
    right: 20%;
    z-index: 2;
    background-color: white;
    padding: 107px 50px;
    
}
</style>