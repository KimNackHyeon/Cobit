<template>
  <div class='wrap'>
    <!-- <div class="story" @click="clickStory" v-if="openStory">
      <div v-if="story[stageNum-1].start_modal!=''" style="width:100%; height:20%; position:absolute; bottom:50%; display:flex; justify-content:center;">
        <div style="width:20%; height:100%; background-color:white; box-shadow: 1px 1px 14px #000000b3; border: 4px solid #ffcf00;color:black;" v-html="story[stageNum-1].start_modal"></div>
      </div>
      <div style="width: 100%; height: 25%; position: absolute; bottom: 25%;">
        <img style="width:auto; height:100%;" src="../assets/images/pen_saying.gif">
      </div>
      <div class="script" v-html="story[stageNum-1].start"></div>
    </div> -->
    <div class="practice-container">
      <div class="unity-box">
        <div class="stagebtn" @click="gomypage" style="position:absolute; z-index: 3;"><v-icon>mdi-chevron-left</v-icon>마이페이지</div>
        <div class="player-box">
          <div class="coin-area">
            <i class="fas fa-coins"></i>{{ coin }}
          </div>
          <div class="heart-area">
            <i v-for="(on, index) in heart" :key="`on-${index}`" class="fas fa-heart"></i>
            <i v-for="(off, index) in (5-heart)" :key="`off-${index}`" class="far fa-heart"></i>
          </div>
        </div>
        <unity class="unity" style="width:100%; height:100%;" src="free/Build/free.json" unityLoader="free/Build/UnityLoader.js" ref="myInstance" :hideFooter="true"></unity>
      </div>
      <div class="code-box" @drop="drop" @dragover="dragover">
          <!-- <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div> -->
        <div class="block-box">
           <div class="block-list">
            <div v-for="(m,index) in moves" :key="index" class="block" :class="'block'+index" @mouseover="blockmouseover(m,$event)">
              {{m.move_kor}}
              <v-icon style="color:white; float:right; opacity: 60%; height: 100%;" size="2.8vw">{{m.icon}}</v-icon>
            </div>
          </div>
          <!-- <div v-show="isObstacle" class="block-list">
            <div class="block">장애물추가</div>
          </div> -->
        </div>
        <div id="play-box" class="play-box">
          <div class="block-list">
            
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
                <div v-for="(m,index) in resultStep" :class="moves[m.num].isForblock" :index="m.index" :key="index" draggable="true" @dragstart="dragstart(index,$event)" :style="{position:m.position,top: 0,left:0,'margin-left':m.marginleft,'margin-top':m.marginTop}" >
                  <div class="block" :class="'block'+m.num+' '+m.class" @click="clickForblock(m,index)" style="margin-bottom:0px;">
                    {{moves[m.num].move_kor}}
                    <div v-if="m.num==7"  style="background-color: white; width: 48px; height: 70%; margin: 0px 9px; box-shadow: 0px 0px 0px 3px #e58603;border-radius: 7px;">
                      <div @click="choiceLoopNum(index)" style="background-color:#e58603; width:50%; height:100%;float:left"><v-icon style="color:white">mdi-menu-down</v-icon>
                      </div>
                      <div style="width: 50%; height: 100%; float: right; color: black; text-align: center;">{{m.loop}}</div>
                      <div v-if="m.choiceNum" class="choiceNum">
                        <div v-for="i in (1,10)" :key="i" @click="selectLoopNum(i,index)" style="color: black; text-align: center; border-bottom: 1px solid #e3e1e1; height: 27px;">
                          {{i}}
                        </div>
                      </div>
                    </div>
                    <!-- <v-icon style="color:white; float:right; opacity: 60%" size="4vw">{{moves[m.num].icon}}</v-icon> -->
  
                    <v-icon style="color:white; float:right; opacity: 60%; height:100%;" size="2.8vw" >{{moves[m.num].icon}}</v-icon>
                  </div>  
                  <div class="block" style="background-color:gray;margin-bottom:0px;" v-if="(m.num==7&&m.onclick)||(m.num==8&&m.onclick)" :style="{display:m.overMe}"></div>
                  <div class="block" :class="'underForblock under'+index" v-if="m.num==7||m.num==8" style="background-color:orange;margin-bottom:0px;height:20px;"></div>
                  <div class="block" style="background-color:gray;margin-bottom:0px;" v-if="!((m.num==7&&m.onclick)||(m.num==8&&m.onclick))" :style="{display:m.overMe}">
                  </div>
                  <!-- <div v-if="m.class=='activate'" style="position:fixed;display:flex; justify-content:center;width:100%;height:5%; margin-left:15%; margin-top: 14%; z-index:3;">
                    <div style="width:50%; height:100%; background-color:#00000096; text-align:center;color:white;">반복문이 활성화 되었습니다.<br>반복문 안에 블록을 넣을 수 있습니다.</div>
                  </div>   -->
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import FailModal from '../components/FailModal.vue';
import { mapMutations } from 'vuex';
import axios from 'axios';
import store from '../vuex/store';

export default {
  name: 'CodeBlock',
  data() {
    return {
      isFail: false,
      heart: 5,
      coin: 0,
      distX: '',
      distY: '',
      underfor:[],
        defaultStep:[
        {
          num:0,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'10px',
          index:0,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:1,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'65px',
          index:1,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:2,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'120px',
          index:2,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:3,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'175px',
          index:3,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:4,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'230px',
          index:4,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:5,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'285px',
          index:5,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:6,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'340px',
          index:6,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:7,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'395px',
          index:7,x:0,y:0,son:-1,onPlayBtn:false,loop:1,overmeFor:false
        },
        {
          num:8,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'450px',
          index:8,x:0,y:0,son:-1,onPlayBtn:false,loop:1,overmeFor:false
        }
      ],
      moves:[
        {
          num:0,
          move:'Up',
          move_kor:'위',
          icon:'mdi-arrow-up-bold-circle',
        },
        {
          num:1,
          move:'Right',
          move_kor:'오른쪽',
          icon:'mdi-arrow-right-bold-circle',
          isForblock:''
        },
        {
          num:2,
          move:'Left',
          move_kor:'왼쪽',
          icon:'mdi-arrow-left-bold-circle',
          isForblock:''
        },
        {
          num:3,
          move:'Down',
          move_kor:'아래',
          icon:'mdi-arrow-down-bold-circle',
          isForblock:''
        },
        {
          num:4,
          move:'TurnRight',
          move_kor:'오른쪽 90˚ 회전',
          // icon:'mdi-rotate-right-variant',
          isForblock:''
        },
        {
          num:5,
          move:'TurnLeft',
          move_kor:'왼쪽 90˚ 회전',
          // icon:'mdi-rotate-left-variant',
          isForblock:''
        },
        {
          num:6,
          move:'Jump',
          move_kor:'점프',
          icon:'mdi-trending-up',
          isForblock:''
        },
        {
          num:7,
          move:'Loop',
          move_kor:'반복',
          isForblock:'forblock'
        },
        {
          num:8,
          move:'If',
          move_kor:'조건',
          isForblock:''
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
      playson:-1,
      alreadyOverPlay:false,
      history:[],
      clickhistory:false,
      showhint: false,
      clickhint:false,
      starNum: 1,
      stageType: 1,
      openStory:true,
      buyhint: false,
      fori : 0,
    }
  },
  components: {
    Unity,
    FailModal,
  },
  computed: {
  },
  async created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('fail', this.handleFail)
    window.addEventListener('coin', this.handleCoin)
    window.addEventListener('heart', this.handleHeart)
    if(this.$cookies.isKey("access_token")){
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
                this.hintCount = res.data.hint;
              });
    }

  },
  mounted() {
    // this.onMove();
  },
  watch: {
  },
  methods: {
     ...mapMutations([]),
     blockmouseover(m,event){
      let posX = event.pageX;
      let posY = event.pageY;
      // console.log(event.target);
      // event.dataTransfer.effectAllowed = 'copyMove';
      // event.dataTransfer.dropEffect = "copy";
      this.targetdiv = event.target;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.selectnum = event.target.className;
      this.isAdded = false;
      this.isOnMove = true;//움직이고있다.
      // var selectedNum = this.selectnum;
      // var selectedNum = this.selectnum.split("block")[2].split(' ')[0]
      var selectedNum = m.num;
      console.log(selectedNum);
      if(Number(selectedNum)==7){
        // this.underfor.push({parentNum:this.resultStep.length,sonNum:0,x:posX + this.distX,y:posY + this.distY+45,overMe:false})
        this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 'px',marginTop:posY + this.distY + 'px',class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false,forindex:this.fori,forson:-1});
        this.fori+=1;
      }else{
        this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
      }
     },
     selectLoopNum(loopnum,mynum){
       this.resultStep[this.targetdivNum].loop = loopnum;
       this.resultStep[this.targetdivNum].choiceNum = false;
       this.targetdivNum = mynum;
     },
     choiceLoopNum(mynum){
       this.resultStep[this.targetdivNum].choiceNum = true;
       this.targetdivNum = mynum;
     },
     clickForblock(m,index){
       if(m.num==7 ||m.num==8 ||index==101){
         if(m.onclick){
           m.onclick = false;
           m.class = '';
         }else{
           m.onclick = true;
          m.class = 'activate';
         }
       }
     },
     deleteAll(){
       this.resultStep = [];
     },
    clickPlayBtn(){
      var tempson = this.playson;
      var resultBlocknum=0;  //최종 블록 수
      var delNode = [];
      var forlist = [];
      this.resultmoves = [];
      while(tempson != -1){
        resultBlocknum +=1;
        // if(this.resultStep[tempson].num!=7){
          delNode.push(this.resultStep[tempson].index);
          if(this.resultStep[tempson].num==7){
            var tempforson = this.resultStep[tempson].forson;
            var ForresultString = String(this.resultStep[tempson].loop);
            while(tempforson !=-1){
              resultBlocknum +=1;
              ForresultString+=',';
              console.log(this.resultStep);
              console.log(this.resultStep[tempforson]);
              console.log("tempforson: "+tempforson);
              ForresultString += this.moves[this.resultStep[tempforson].num].move;
              if(this.resultStep[tempforson].num!=7||this.resultStep[tempforson].son!=-1){
                tempforson = this.resultStep[tempforson].son;
              }else{
                tempforson = this.resultStep[tempforson].forson;
              }
            }
            this.resultStep[tempson].forindex = forlist.length;
            // console.log(ForresultString);
            forlist.push(ForresultString);
          }
          this.resultmoves.push({move:this.resultStep[tempson],loop:this.resultStep[tempson].loop});
          tempson = this.resultStep[tempson].son;
          console.log("최종 블록 수:"+resultBlocknum);
      }

      // console.log("resultStep["+0+"]="+this.resultStep[0].son);
      this.resultmoves.forEach( step => {
        if(step.move.num!=7){
          this.$refs.myInstance.message('JavascriptHook',this.moves[step.move.num].move);
          // console.log(this.moves[step.move.num].move);
        }else{
          this.$refs.myInstance.message('JavascriptHook',this.moves[step.move.num].move,forlist[step.move.forindex]);
          // console.log(this.moves[step.move.num].move+','+forlist[step.move.forindex]);
        }
      });
     for(var i=0; i<this.resultmoves.length;i++){
       this.history.push(this.resultmoves[i].move);
     }
    
     
     this.resultStep = [];
     this.playClass.background='#1dc360';
     this.$refs.myInstance.message('JavascriptHook',"Go");
    //  console.log(delNode)
    //  for(var j=0; j<delNode.length;j++){
    //    this.resultStep.pop(j);
    //  }
      this.playson = -1;
      this.alreadyOverPlay = false;
      // console.log(this.resultmoves);
    },
    // clickHint(){
    //   if(this.clickhint){
    //     this.showhint = 'none';
    //     this.clickhint = false;
    //   }else{
    //     this.showhint = 'block';
    //     this.clickhint = true;
    //   }
    // },
    getNeighbor(event){
      var x = this.distX+event.pageX;
      var y = this.distY+event.pageY;
      var playtarget = document.getElementById('play');
      const playRect = playtarget.getBoundingClientRect();

      // for(var f=0;f<this.underfor.length;f++){
      //   if(x>this.underfor[f].x&&x<this.underfor[f].x+50&&y>this.underfor[f].y&&y<this.underfor[f].y+30){
      //     this.underfor[f].overMe=true;
      //   }else{
      //     this.underfor[f].overMe=false;
      //   }
      // }
      // console.log(x+','+y)
      // console.log("underfor[0]"+this.underfor[0].x+" "+this.underfor[0].y)
      
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
      
      // underForblocksPositions.forEach(step=>{
      //   var stepx = step.x;
      //   var stepy = step.y;
      //   if(x<stepx+step.width&&x>stepx&&y>stepy&&y<stepy+step.height){
      //     var tempclass = 
      //   }
      // });

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
    // onMove() {
    //   this.isMove = true; this.isObstacle = false
    //   // const MOVE = document.querySelector('.move-menu');
    //   // const OBSTACLE = document.querySelector('.obstacle-menu');
    //   // MOVE.classList.add('on-menu-bar');
    //   // OBSTACLE.classList.remove('on-menu-bar');
    // },
    // onObstacle() {
    //   this.isObstacle = true; this.isMove = false
    //   const MOVE = document.querySelector('.move-menu');
    //   const OBSTACLE = document.querySelector('.obstacle-menu');
    //   // MOVE.classList.remove('on-menu-bar');
    //   // OBSTACLE.classList.add('on-menu-bar');
    // },
                                              
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
          this.resultStep[this.targetdivNum].marginleft = posX + this.distX + 'px';
          this.resultStep[this.targetdivNum].marginTop = posY + this.distY + 'px';
          this.resultStep[this.targetdivNum].x = posX + this.distX;
          this.resultStep[this.targetdivNum].y = posY + this.distY;
          this.updateLink();
          // if(this.resultStep[this.targetdivNum].num == 7){
          //   this.underfor[this.resultStep[this.targetdivNum].forindex].x = this.resultStep[this.targetdivNum].x;
          //   this.underfor[this.resultStep[this.targetdivNum].forindex].y = this.resultStep[this.targetdivNum].y+50;
          // }
          
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
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          this.resultStep[this.targetdivNum].position = 'unset';
          console.log(content[0]);
          
        }
        // var original_son = -1;
        //x와 y를 움직이기

        this.resultStep.some( step => {
          if(step.overMe=='block' || step.class=='overMe'){
            if(step.num!=7 && step.num!=8){
            content[0].nextSibling.after(this.targetdiv);
            var os = this.resultStep[step.index].son;
                this.resultStep[step.index].son = this.targetdivNum;
                this.resultStep[this.targetdivNum].son = os;
                var parent = step.index;
                var son = this.targetdivNum;
                while(son != -1){
                  this.resultStep[son].x = Number(this.resultStep[parent].x);
                  this.resultStep[son].y = this.resultStep[parent].y+47;
                  parent = son;
                  son = this.resultStep[son].son;
                }
          }else{
              var underForblockParent = content[0].parentNode;
              var underForblock = underForblockParent.getElementsByClassName("underForblock")[0];
              console.log(underForblock);
              if(!step.onclick){
                underForblock.nextSibling.after(this.targetdiv);
                os = this.resultStep[step.index].son;
                this.resultStep[step.index].son = this.targetdivNum;
                this.resultStep[this.targetdivNum].son = os;
                parent = step.index;
                son = this.targetdivNum;
                while(son != -1){
                  this.resultStep[son].x = Number(this.resultStep[parent].x);
                  this.resultStep[son].y = this.resultStep[parent].y+47;
                  parent = son;
                  son = this.resultStep[son].son;
                }
              }else{
                underForblock.nextSibling.before(this.targetdiv);
                 os = this.resultStep[step.index].forson;
                this.resultStep[step.index].forson = this.targetdivNum
                this.resultStep[this.targetdivNum].forson = os;

                parent = step.index;
                son = this.targetdivNum;
                while(son != -1){
                  this.resultStep[son].x = Number(this.resultStep[parent].x);
                  this.resultStep[son].y = this.resultStep[parent].y+47;
                  parent = son;
                  son = this.resultStep[son].forson;
                }
              }
          }
            // console.log("원래"+step.index+"의 son "+this.resultmoves[step.index].son+"을 "+this.targetdivNum+"로 바꿈");
            // console.log(this.targetdivNum+"의 son을"+os+"로 바꿈");
            step.overMe = 'none';
            console.log()
          }
          if(step.overMe=='block' || step.class=='overMe')return;
     });
    //  this.resultStep[this.targetdivNum].son = original_son;
    console.log(this.resultStep);
     this.playClass.show='none';
      }

    },
    reStart() {
      this.coin = 0;
      this.heart = 5;
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    handleStart() {
      setTimeout(() => {
        this.loadMyCharacter();
      }, 10);
    },
    handleCoin() {
      this.coin = event.detail.coin;
    },
    handleHeart() {
      this.heart = event.detail.heart;
    },
    handleFail() {
      this.onModal2();
    },
    onModal2() {
      this.isFail = true;
    },
    gomypage(){
      this.$router.push('/mypage')
    },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      console.log("캐릭터 정보 불러오기");
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        console.log(res);
        this.$refs.myInstance.message('body', 'ChangeColor', res.data.color);
        this.$refs.myInstance.message('Penguin', 'ChangeEyebrow', res.data.eyebrow)
        this.$refs.myInstance.message('Penguin', 'ChangeEye', res.data.eye)
        if(res.data.crown){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.crown)
        }
        if(res.data.shield){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.shield)
        }
        if(res.data.sword){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.sword)
        }
      })
    },
  },
  beforeDestroy () {
    window.removeEventListener('start', this.handleStart)
    window.removeEventListener('fail', this.handleFail)
    window.removeEventListener('coin', this.handleCoin)
    window.removeEventListener('heart', this.handleHeart)
  },
}
</script>

<style scoped>
.wrap{
  padding:0px;
  width:100%;
  height:100%;
}
.practice-container {
  /* display: flex; */
  /* margin-top: 100px; */
  width: 100%;
  height:100%;
}

.practice-container .unity-box {
  float: left;
  width: 60%;
  /* margin-right: 1%; */
  height: 100%;
  /* background-color: grey; */
  position: relative;
}

.practice-container .code-box {
  width: 40%;
  height: 92%;
  /* background-color: bisque; */
  /* display: flex; */
  position: relative;
  float: right;
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
  background-color: #f7f7f76e;
  border: 1px solid #dcdcdc94;
  /* position: relative; */
  background-image: url('../assets/images/tile.png');
  background-repeat: repeat;
}

.block-menu-bar {
  margin-left:-100px;
  width: 100px;
}

.block-box .block-list {
  width: 100%;
  padding: 10px;
  background-color: #f5f5f5;
  border: 1px solid #dcdcdc94;
  overflow-y: auto;
  box-shadow:1px 0px 13px #0000000f;
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
  border: 1px solid #dcdcdc94;
  margin-bottom: 5px;
  cursor: pointer;
  transition: background-color .5s ease;
}

.block {
  width:105px;
  height:45px;
  text-align: left;
  padding: 5px 0px 5px 10px;
  display: flex;
  align-items: center;
  border-radius: 20px;
  background-color: rgb(76, 151, 255);
  /* border:1px solid rgb(51, 115, 204); */
  margin-bottom: 10px;
  cursor: pointer;
  color: #fff;
  font-size: 14px;
  float: left;
  clear: both;
  text-shadow: 1px rgb(51, 115, 204);
      overflow: hidden;
}

/* .block::before {
  content: "";
  position: absolute;
  right: 50%;
  top: -20%;
  transform: translate(50%, 0);
  width: 15px;
  height: 15px;
  background-color: rgb(76, 151, 255);
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
}

.block::after {
  content: "";
  position: absolute;
  right: 50%;
  bottom: -20%;
  transform: translate(50%, 0);
  width: 15px;
  height: 15px;
  background-color: #fff;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
} */

.play-box .play {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 50px;
  background-color: bisque;
}

.practice-container .unity-box .unity {
  width: 100%;
  height: 100%;
}

.overMe{
  background-color: rgb(20 121 165);
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
  display: inline-block;
  width: 120px;
  height: 45px;
  margin: 1% 5% 1% 3%;
}
.historyBtnbox {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: -webkit-gradient(linear, left top, left bottom, from(#fdc9c4), to(#f2928d));
  border-radius: 25px;
  font-family: 'BMJUA';
  font-size: 18px;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff;
  color: black;
  cursor: pointer;
}
#hintBtn{
  display: inline-block;
  width: 120px;
  height: 45px;
  margin: 1% 3% 1% 5%;

}
.hintBtnbox {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: -webkit-gradient(linear, left top, left bottom, from(#ffeba1), to(#ffdf65));
  border-radius: 25px;
  font-family: 'BMJUA';
  font-size: 18px;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff;
  color: black;
  cursor: pointer;
}
#history{
    position: absolute;
    width: 50%;
    height: 90%;
    left: 25%;
    z-index: 2;
    background-image: url('../assets/images/history.png');
    background-color: transparent;
    background-size: 100% 100%;
    padding: 107px 50px;
}

#hint{
    position: absolute;
    width: 20%;
    height: 30%;
    top: 0;
    right: 40%;
    z-index: 2;
    background-color: white;
    /* padding: 107px 50px; */
    
}
#deleteAllBtn {
  display: inline-block;
  width: 120px;
  height: 45px;
  margin: 1% 3% 1% 5%;
  float: right;
}
.deleteAllBtnbox {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: -webkit-gradient(linear, left top, left bottom, from(#bcf2ff), to(#60e5ff));
  border-radius: 25px;
  font-family: 'BMJUA';
  font-size: 18px;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff;
  color: black;
  cursor: pointer;
}
.story{
  width:100vw;
  height:100vh;
  position:absolute;
  bottom:0;
  z-index:1;
}
.script{
      width: 100%;
    height: 25%;
    background-color: rgba(0, 0, 0, 0.66);
    position: absolute;
    bottom: 0px;
    color: white;
    font-size: x-large;
    padding: 5vh 5vw;
}

.block7{
  background-color: orange;
}

.block72{
  background-color: orange;
  height:20px;
}

.block8{
  background-color: orange;
}

.forblock{
  background-color: #ffa5004d;
  border-radius: 20px;
}
.choiceNum{
  width: 60px;
    height: 100px;
    background-color: white;
    margin-top: -22px;
    position: absolute;
    margin-left: -3px;
    overflow-y: scroll;
    z-index:4;
}
.choiceNum::-webkit-scrollbar {
    width: 5px;
  }
.choiceNum::-webkit-scrollbar-thumb {
    background-color: gray;
  }
.container::-webkit-scrollbar-track {
    background-color: black;
  }

.activate{
  background-color:orangered;
}
.stagebtn {
  top: 10px;
  left: 10px;
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
  box-shadow: 6px 6px 10px -1px #ffffff;
}
.stagebtn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
.btnsbox {
  height: 8%;
  width: 40%;
  float: right;
}

.unity-box .player-box {
  position: absolute;
  top:20px;
  right:10px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  z-index: 3;
  padding: 10px;
  border-radius: 20px;
}

.player-box i {
  font-size: 20px;
  margin: 0 2px;
}

.player-box .heart-area {
  color: red;
}

.player-box .coin-area {
  margin-right: 30px;
  font-family: 'BMJUA';
}

.player-box .coin-area > i {
  color: gold;
  margin-right: 10px;
}
</style>