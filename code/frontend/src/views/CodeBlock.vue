<template>
  <div class='wrap'>
    <div class="story" @click="clickStory" v-if="openStory">
      <div v-if="story[stageNum-1].start_modal!=''" style="width:100%; height:20%; position:absolute; bottom:50%; display:flex; justify-content:center;">
        <div style="width:20%; height:100%; background-color:white; box-shadow: 1px 1px 14px #000000b3; border: 4px solid #ffcf00;color:black;" v-html="story[stageNum-1].start_modal"></div>
      </div>
      <div style="width: 100%; height: 25%; position: absolute; bottom: 25%;">
        <img style="width:auto; height:100%;" src="../assets/images/pen_saying.gif">
      </div>
      <div class="script" v-html="story[stageNum-1].start"></div>
    </div>
    <div class="code-block-container">
      <div class="unity-box">
        <div class="stagebtn" @click="gostage" style="position:absolute; z-index: 3;"><v-icon>mdi-chevron-left</v-icon>스테이지</div>
        <unity class="unity" style="width:100%; height:100%;" src="cobit/Build/cobit.json" unityLoader="cobit/Build/UnityLoader.js" ref="myInstance" :hideFooter="true"></unity>
        <VueSpeech style="position:absolute; z-index: 3; bottom:0;"></VueSpeech>
      </div>
      <div class="btnsbox">
        <div id="hintBtn" @click="buyHint">
          <div class="hintBtnbox">
            <v-icon style="color: black">mdi-lightbulb-on</v-icon>
            힌트 {{hintCount}}
          </div>
        </div>
        <div id="hint" v-if="showhint">
          <div  style="height: 100%">
            <img :src="require(`../assets/images/${story[stageNum-1].hint}`)" alt="hint" style="width:100%; height:100%; border: 3px solid black;">
          </div>
          <div style="position: absolute; top: 3px; right: 3px;" @click="showhint=false"><v-icon style="font-size: 30px; color: white; cursor: pointer">mdi-close</v-icon></div>
            <!-- <div v-if="hint==''">해당 스테이지의 힌트가 없습니다.</div> -->
        </div>
        <div id="historyBtn" @click="clickHistory" @mouseover="openHistory" @mouseout="closeHistory">
          <div class="historyBtnbox">
            <v-icon style="color: black">mdi-history</v-icon>
            히스토리
          </div>
        </div>
        <div id="history" :style="{display:this.showhistory}" transition="scale-transition">
          <div style="width:100%; height:100%; overflow-y:auto;">
            <div v-for="(step,index) in history" :key="index" >
              {{step.move_kor}}
            </div>
            <div v-if="history.length==0">히스토리 내역이 없습니다.</div>
          </div>
        </div>
        <div @click="deleteAll" id="deleteAllBtn">
          <div class="deleteAllBtnbox">
            <v-icon style="color: black">mdi-trash-can-outline</v-icon>
            전체 삭제
          </div>
        </div>
      </div>
      <div class="code-box" @drop="drop" @dragover="dragover">
          <!-- <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div> -->
        <div class="block-box">
           <div v-show="isMove" class="block-list">
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
    <ClearModal v-if="isClear" @close="isClear= false" @restart="reStart" @next="nextLevel"/>
    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import ClearModal from '../components/ClearModal.vue';
import FailModal from '../components/FailModal.vue';
import { mapMutations } from 'vuex';
import axios from 'axios';
import store from '../vuex/store';
import Swal from 'sweetalert2';

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
      underfor:[],
      story:[
        { start_modal:"cobit에 오신 여러분들 환영해요!<br> 우선, 오른쪽에 있는 컴퓨터에 다가가 왜 고장이 났는지 살펴볼까요?",
          start:"1. 어떻게 풀어야할지 마이크를 누르고 말해봐.<br> 2. 블록 꾸러미에서 원하는 블록을 꺼내어 '실행' 블록과 연결해 봐.<br> 3. 다 조립했으면 '실행'을 눌러봐.<br> 4. 나는 네가 조립한 블록대로 위에서부터 순서대로 움직일게.",
          end:"<h3>컴퓨터의 두뇌, CPU</h3><br>CPU는 컴퓨터의 두뇌에요. '프로세서'라고도 불린답니다. <br> 모든 장치에 제어와 연산을 하도록 조종하는 CPU는 컴퓨터가 빠르게 돌아가는데에 중요한 역할을 한답니다.",
          hint:"basicHint1.png"
          },
          { start_modal:"",
          start:"컴퓨터의 두뇌인 CPU에 대해 알아보았어요.<br>컴퓨터를 조금 더 살펴보기위해 다가가보세요!",
          end:"<h3>컴퓨터의 공부 책상, 메모리</h3><br>공부할 때 교과서나 참고서, 필기도구 등을 책상위에 올려놓고 참고서를 보면서 공책에 필기를 하죠?<br>메모리 또한 컴퓨터가 작업에 필요한 것을 일시적으로 기억해서 CPU가 프로그램을 작동시킬 때 사용할 수 있게 합니다!",
          hint:"basicHint2.png"
          },
          { start_modal:"",
          start:"앗! 이게뭐지?<br>갑자기 이상한게 나타났어요! 이게 뭘까요?<br>알아보기위해 다가가보세요.",
          end:"<h3>I'm the creeper, catch me if you can!</h3>크리퍼, 최초의 컴퓨터 바이러스.<br>컴퓨터 바이러스란, <br>우리몸을 아프게하는 바이러스와 비슷하게 스스로를 복제하여 컴퓨터를 아프게하는 바이러스에요.<br>인터넷 이나 네트워크 또는 이동식 매체를 통해 전파가 되어 내 컴퓨터의 파일시스템을 망가트려요!",
          hint:"basicHint3.png"
          },
          { start_modal:"",
          start:"바이러스라니,무서워요! <br>컴퓨터를 망가뜨리는 바이러스의 종류에는 어떤것이 있을까요?<br>살펴보기위해 다가가보세요.",
          end:"<h3>꿈틀거리는 벌레라는 의미의 '웜(Worm)바이러스'</h3>웜바이러스는 스스로 복제를 해서 내부시스템을 망가트리는 나쁜 바이러스에요.<br>웜바이러스는 인터넷속도에는 큰영향을 주지않지만 파일을 변형하거나 예측불가능한 증상을 만드는게 특징이에요.<br> 웜바이러스에 예방하기위해선 이메일첨부파일을 다운로드를 주의해서 해야해요.",
          hint:"basicHint4.png"
          },
          { start_modal:"",
          start:"또다른 바이러스가 나타났어요!<br> 저 바이러스는 뭘까요? 알아보러 가봅시다!",
          end:"<h3>트로이 전쟁 중 성 안에 몰래 침입한전략에서 비유된 '트로이목마'</h3>바이러스 중 가장 유명한 바이러스이며, 정상적인 프로그램으로 위장해 숨어있다가 실행하면 악성코드를 퍼트리는 바이러스에요.<br>해킹기능이 존재하는 바이러스이니 컴퓨터에 트로이목마가 감염되었다면 내 개인정보가 유출되었다는 의미이니 조심하여야합니다.",
          hint:"basicHint5.png"
          },
      ],
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
      showhistory:'none',
      clickhistory:false,
      showhint: false,
      clickhint:false,
      hint:"스테이지의 힌트",
      starNum: 1,
      stageType: 1,
      openStory:true,
      buyhint: false,
      hintCount: store.state.kakaoUserInfo.hint,
      fori : 0,
    }
  },
  components: {
    Unity,
    ClearModal,
    FailModal,
  },
  computed: {
  },
  async created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('clear', this.handleClear)
    window.addEventListener('fail', this.handleFail)
    this.stageNum = this.$cookies.get('stageInfo').stageNum;
    this.stageType = this.$cookies.get('stageInfo').stageType;
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
     ...mapMutations(['setInStageNum', 'setInStageStar', 'setIsLastStage', 'setCode', 'setCodeKor']),
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
     clickStory(){
       this.openStory = false;
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
    buyHint(){
      console.log(store.state.kakaoUserInfo);
      if(this.buyhint == false){
        axios.post(`https://k3b102.p.ssafy.io:9999/cobit/user/hint`,store.state.kakaoUserInfo)
        .then(()=>{
          this.hintCount -= 1;
          this.buyhint = true;
          this.showhint = true;
        })
      }else if(this.hintCount <= 0 && this.buyhint == false) {
        // alert('힌트 구매 불가!');
        Swal.fire(
          '힌트 갯수가 부족합니다.',
          '',
          'warning'
        )
        this.buyhint = false;
      }else {
        this.buyhint = true
        this.showhint = true
      }
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
    getStar() {
      const LENG = this.commandList.length
      if(this.stageNum == 1) {
        if(LENG <= 5) {this.starNum=3} else if(LENG > 5 && LENG <= 7) {this.starNum=2}
      } if(this.stageNum == 2) {
        if(LENG <= 9) {this.starNum=3} else if(LENG > 9 && LENG <= 12) {this.starNum=2}
      } if(this.stageNum == 3) {
        if(LENG <= 13) {this.starNum=3} else if(LENG > 13 && LENG <= 16) {this.starNum=2}
      } if(this.stageNum == 4) {
        if(LENG <= 12) {this.starNum=3} else if(LENG > 12 && LENG <= 15) {this.starNum=2}
      } if(this.stageNum == 5) {
        if(LENG <= 16) {this.starNum=3} else if(LENG > 16 && LENG <= 20) {this.starNum=2}
      }

      // axios
      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/stage/user`,{
        userId : store.state.kakaoUserInfo.id,
        stageId : this.stageType + "" + this.stageNum,
        star : this.starNum 
      });
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
    LevelLoad() {
      this.commandList = []
      if (this.stageType == 1) {
        this.$refs.myInstance.message('JavascriptHook', 'Stage', this.stageNum)
      } else if (this.stageType == 2) {
        this.$refs.myInstance.message('JavascriptHook', 'MiddleStage', this.stageNum)
      }
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
        this.loadMyCharacter();
      }, 10);
    },
    handleClear() {
      console.log('clear')
      this.getStar();
      this.onModal();
      this.history.push({move:'clear',move_kor:"스테이지"+this.stageNum+' 성공!',num:-1})
    },
    handleFail() {
      this.onModal2();
      this.history.push({move:'fail',move_kor:"스테이지"+this.stageNum+' 실패!',num:-2})
    },
    onModal() {
      this.isClear = true;
      this.makeCode();
      this.setInStageNum(this.stageNum);
      this.setInStageStar(this.starNum);
      if(this.stageType == 1 && this.stageNum == 5) {
        this.setIsLastStage(true)
      } else if (this.stageType == 2 && this.stageNum == 4) {
        this.setIsLastStage(true)
      }
    },
    onModal2() {
      this.isFail = true;
    },
    makeCode(){
      console.log(this.resultmoves);
      var code = [];
      var code_kor = [];
      this.resultmoves.forEach(m => {
        code.push(m.move.move + "();");
        code_kor.push(m.move.move_kor + "();");
      });
      this.setCode(code)
      this.setCodeKor(code_kor)
      console.log(code, '1');
      console.log(code_kor, '2');

    },
    gostage(){
        this.$router.push('/gamemap')
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
  /* display: flex; */
  /* margin-top: 100px; */
  width: 100%;
  height:100%;
}

.code-block-container .unity-box {
  float: left;
  width: 60%;
  /* margin-right: 1%; */
  height: 100%;
  /* background-color: grey; */
}

.code-block-container .code-box {
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

.code-block-container .unity-box .unity {
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
</style>