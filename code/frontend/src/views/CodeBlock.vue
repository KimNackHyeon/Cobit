<template>
  <div class='wrap'>
    <div class="story" @click="clickStory" v-show="openStory && stageType == 1">
      <div v-show="story[stageNum-1].start_modal!=''" style="width:100%; height:20%; position:absolute; bottom:50%; display:flex; justify-content:center;">
        <div style="width:20%; height:100%; background-color:white; box-shadow: 1px 1px 14px #000000b3; border: 4px solid #ffcf00;color:black;" v-html="story[stageNum-1].start_modal"></div>
      </div>
      <div style="width: 100%; height: 25%; position: absolute; bottom: 25%;">
        <img style="width:auto; height:100%;" src="../assets/images/pen_saying.gif">
      </div>
      <div class="script" v-html="story[stageNum-1].end"></div>
    </div>
    <div class="story" @click="clickStory" v-show="openStory2 && stageType == 2">
      <div v-show="story2[stageNum-1].start_modal!=''" style="width:100%; height:20%; position:absolute; bottom:50%; display:flex; justify-content:center;">
        <div style="width:20%; height:100%; background-color:white; box-shadow: 1px 1px 14px #000000b3; border: 4px solid #ffcf00;color:black;" v-html="story2[stageNum-1].start_modal"></div>
      </div>
      <div style="width: 100%; height: 25%; position: absolute; bottom: 25%;">
        <img style="width:auto; height:100%;" src="../assets/images/pen_saying.gif">
      </div>
      <div v-show="openStory2Start" class="script" v-html="story2[stageNum-1].start"></div>
      <div v-show="openStory2End" class="script" v-html="story2[stageNum-1].end"></div>
    </div>
    <!-- 튜토리얼 -->
    <div class="tutorial1" v-if="showTutorial == 1 && stageType == 1 && stageNum == 1">
      <div class="balloon1">
        <div class="balloon1text">
          <div class="balloontext1">1. 힌트 버튼</div>
          <div class="balloontext2">힌트 버튼을 누르면 힌트를 얻을 수 있습니다.</div>
          <div class="tutorialnextbtn" @click="tutorial1nextbtn">다음</div>
        </div>
      </div>
    </div>
    <div class="tutorial2" v-if="showTutorial == 2 && stageType == 1 && stageNum == 1">
      <div class="balloon2">
        <div class="balloon1text">
          <div class="balloontext1">2. 히스토리 버튼</div>
          <div class="balloontext2">히스토리 버튼을 누르면 실행 내역을 볼 수 있습니다.</div>
          <div class="tutorialnextbtn" @click="tutorial2nextbtn">다음</div>
        </div>
      </div>
    </div>
    <div class="tutorial3" v-if="showTutorial == 3 && stageType == 1 && stageNum == 1">
      <div class="balloon3">
        <div class="balloon1text">
          <div class="balloontext1">3. 전체삭제 버튼</div>
          <div class="balloontext2">전체삭제 버튼을 누르면 보드에 있는 블럭이 모두 삭제됩니다.</div>
          <div class="tutorialnextbtn" @click="tutorial3nextbtn">다음</div>
        </div>
      </div>
    </div>
    <div class="tutorial4" v-if="showTutorial == 4 && stageType == 1 && stageNum == 1">
      <div class="balloon4">
        <div class="balloon1text">
          <div class="balloontext1">4. 블록 옮기기</div>
          <div class="balloontext2">펭귄이 컴퓨터에 다가갈 수 있도록 보드에 오른쪽 블록을 4개 연결해주세요.</div>
          <div class="showbtn" @click="deleteAll" style="margin-left: 2%">다시하기</div>
          <div class="showbtn" @click="tutorial4" @mouseover="tutorial4_0">미리보기</div>
        </div>
      </div>
    </div>
    <div class="tutorial5" v-if="showTutorial == 5 && stageType == 1 && stageNum == 1">
      <div class="balloon5">
        <div class="balloon1text">
          <div class="balloontext1">5-1. 게임 실행</div>
          <div class="balloontext2">보드에 만든 블럭을 시작 버튼에 연결합니다.</div>
        </div>
      </div>
    </div>
    <div class="tutorial6" v-if="showTutorial == 6 && stageType == 1 && stageNum == 1">
      <div class="balloon6">
        <div class="balloon1text">
          <div class="balloontext1">5-2. 게임 실행</div>
          <div class="balloontext2">시작 버튼을 누르면 게임이 실행됩니다.</div>
        </div>
      </div>
    </div>
    <!-- for문 튜토리얼 -->
    <div>

    </div>
    <div class="code-block-container">
      <div class="unity-box">
        <div class="stagebtn" @click="gostage" style="position:absolute; z-index: 3;"><v-icon>mdi-chevron-left</v-icon>스테이지</div>
        <unity class="unity" style="width:100%; height:100%;" src="glacier/Build/glacier.json" unityLoader="glacier/Build/UnityLoader.js" ref="myInstance" :hideFooter="true"></unity>
      </div>
      <div class="btnsbox">
        <div id="hintBtn" @click="buyHint">
          <div class="hintBtnbox">
            <v-icon style="color: black">mdi-lightbulb-on</v-icon>
            힌트 {{hintCount}}
          </div>
        </div>
        <div id="hint" v-if="showhint && stageType==1">
          <div  style="height: 100%">
            <img :src="require(`../assets/images/${story[stageNum-1].hint}`)" alt="hint" style="width:100%; height:100%; border: 3px solid black;">
          </div>
          <div style="position: absolute; top: 3px; right: 3px;" @click="showhint=false"><v-icon style="font-size: 30px; color: white; cursor: pointer">mdi-close</v-icon></div>
            <!-- <div v-if="hint==''">해당 스테이지의 힌트가 없습니다.</div> -->
        </div>
        <div id="hint2" v-if="showhint && stageType==2">
          <img :src="require(`../assets/images/${story2[stageNum-1].hint}`)" alt="hint" style="width:100%; border: 3px solid black;">
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
          <div v-if="isBlock1" class='block-mark-area'></div>
          <div v-if="isBlock2" class='block-mark-area2'></div>
          <div v-show="isMove" class="block-list">
            <div v-for="(m,index) in moves" :key="index" @mouseover="blockmouseover(m,$event)" style="position: relative">
              <div v-if="index != 4 && index != 5" class="block" :class="'block'+index">
                {{m.move_kor}}
                <v-icon style="color:white; float:right; opacity: 60%; height: 100%;" size="2.8vw">{{m.icon}}</v-icon>
              </div>
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
                  <!-- <div class="block" style="background-color:gray;margin-bottom:0px;" v-if="!((m.num==7&&!m.onclick)||(m.num==8&&!m.onclick))" :style="{display:m.overMe}"></div> -->
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
import $ from 'jquery';

export default {
  name: 'CodeBlock',
  data() {
    return {
      isClear: false,
      isFail: false,
      stageNum: this.$cookies.get('stageInfo').stageNum,
      isMove: true,
      isObstacle: false,
      isBlock1: false,
      isBlock2: false,
      distX: '',
      distY: '',
      underfor:[],
      blockNum: 0,
      story:[
        { start_modal:"",
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
      story2:[
        {
          start_modal: "",
          start:"메일이 왔어요!<br>누구한테서 온거지?<br>한번 열어볼까요?",
          end:"<h3>낚시와 메일의 합성어인 '피싱메일(Fishing Mail)'</h3><br>피싱메일은 일반메일처럼 보이나 사용자를 속여 바이러스를 퍼트립니다.<br>항상 메일이나 메시지를 열람할때는 주의를 합시다!",
          hint: "middleHint1.png"
        },
        {
          start_modal: "",
          start:"바이러스는 미리 차단해야해요<br>차단할 수 있는 방법에는 무엇이 있을까요?",
          end:"<h3>공격을 보호해주는 방화벽!</h3><br>방화벽은 의심스러운 접속을 차단해줘요<br>미리 방화벽을 설정해서 바이러스를 차단해요",
          hint: "middleHint2.png"
        },
        {
          start_modal: "",
          start:"바이러스가 다시 등장했어요!<br>차단하지 못한 바이러스 미리 예방했나요?",
          end:"<h3>파일들을 임시 복제하는 백업!</h3><br>파일들을 미리 임시보관소에 저장을 하면<br>바이러스에 걸려도 복원을 할 수 있어요<br>자주 백업하는 습관을 길러 바이러스를 예방합시다",
          hint: "middleHint3.png"
        },
        {
          start_modal: "",
          start:"이미 감염된 바이러스는 어떻게 하죠?<br>감염된 PC를 치료할 수 있는 방법을 알아봐요",
          end:"<h3>다양한 솔루션을 가진 백신프로그램</h3><br>백신프로그램은 예방과 치료 그리고 관리까지 바이러스의 종합 해결책이에요<br>자신에게 필요한 백신프로그램을 찾아서 꼭 설치를 해줍시다",
          hint: "middleHint4.png"
        },
        {
          start_modal: "",
          start:"",
          end:"",
          hint: ""
        }
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
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'230px',
          index:6,x:0,y:0,son:-1,onPlayBtn:false,loop:1
        },
        {
          num:7,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'285px',
          index:7,x:0,y:0,son:-1,onPlayBtn:false,loop:1,overmeFor:false
        },
        {
          num:8,
          marginleft:'10px',class:'',overMe:'none',position:'absolute',marginTop:'340px',
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
          move_kor:'만약 앞에 장애물이 있다면',
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
      stageType: this.$cookies.get('stageInfo').stageType,
      openStory:false,
      openStory2: true,
      openStory2Start: false,
      openStory2End: false,
      openClear: false,
      buyhint: false,
      hintCount: store.state.kakaoUserInfo.hint,
      showTutorial: 0,
      forTutorial: 0,
      fori : 0,
      ifi: 0,
      code:[]
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
    // console.log(this.starNum + " " + this.stageType);
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
    if(this.stageNum == 1 && this.stageType == 1){
      this.showTutorial = 1;
      $(".hintBtnbox").css('position', 'relative');
      $(".hintBtnbox").css('z-index', '4');
      $(".hintBtnbox").css('box-shadow', "unset");
    }
    if(this.stageNum == 1 && this.stageType == 2){
      this.forTutorial = 1;

    }
    this.checkBlockArea();
    this.openStory2 = true;
    this.openStory2Start = true;
  },
  watch: {
    stageNum() {
      this.checkBlockArea();
    },
  },
  methods: {
    tutorial1nextbtn() {
      this.showTutorial = 2
      $(".hintBtnbox").css('position', 'unset');
      $(".hintBtnbox").css('z-index', 'unset');
      $(".hintBtnbox").css('box-shadow', "6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff");
      $(".historyBtnbox").css('position', 'relative');
      $(".historyBtnbox").css('z-index', '4');
      $(".historyBtnbox").css('box-shadow', "unset");
      $("#history").css('z-index', '4');
    },
    tutorial2nextbtn() {
      this.showTutorial = 3
      $(".historyBtnbox").css('position', 'unset');
      $(".historyBtnbox").css('z-index', 'unset');
      $(".historyBtnbox").css('box-shadow', "6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff");
      $(".deleteAllBtnbox").css('position', 'relative');
      $(".deleteAllBtnbox").css('z-index', '4');
      $(".deleteAllBtnbox").css('box-shadow', "unset");
    },
    tutorial3nextbtn() {
      this.showTutorial = 4
      $(".deleteAllBtnbox").css('position', 'unset');
      $(".deleteAllBtnbox").css('z-index', 'unset');
      $(".deleteAllBtnbox").css('box-shadow', "6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff");
      $(".block1").css("z-index", '3');
      $("#play").css("z-index", "3");
    },
    tutorial4_0() {
      var selectedNum = 1;
      var posX = 790;
      var posY = 12;
      this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'rightblock1',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
      this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'rightblock2',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
      this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'rightblock3',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
      this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'rightblock4',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
    },
    tutorial4() {
      $(".block1").css("z-index", 'unset');
      $(".block-list").children(".block1").css("z-index", "3");
      var divX = $("#play").offset().left;
      var divY = $("#play").offset().top + $("#play").height();
      var blockX = $(".rightblock1").offset().left;
      var blockY = $(".rightblock1").offset().top;
      for(var i=1; i<5; i++){
        $(`.rightblock${i}`).css("position", 'relative');
        $(`.rightblock${i}`).css("z-index", '4');
      }
      $(".rightblock1").css("transform", `translate(${divX-blockX}px, ${divY-blockY+25}px)`)
      $(".rightblock1").addClass("tutorial4btn")
      setTimeout(() => {
        $(".rightblock2").css("transform", `translate(${divX-blockX}px, ${divY-blockY+70}px)`)
        $(".rightblock2").addClass("tutorial4btn")
      }, 800)
      setTimeout(() => {
        $(".rightblock3").css("transform", `translate(${divX-blockX}px, ${divY-blockY+115}px)`)
        $(".rightblock3").addClass("tutorial4btn")
      }, 1600)
      setTimeout(() => {
        $(".rightblock4").css("transform", `translate(${divX-blockX}px, ${divY-blockY+160}px)`)
        $(".rightblock4").addClass("tutorial4btn")
      }, 2400)
      setTimeout(() => {
        for(var j=1; j<5; j++){
          $(`.rightblock${j}`).css("transform", "")
          $(`.rightblock${j}`).removeClass("tutorial4btn")
        }
      }, 4500);
    },
     ...mapMutations(['setInStageNum', 'setInStageStar', 'setIsLastStage', 'setCode', 'setCodeKor']),
     blockmouseover(m,event){
      if(this.showTutorial == 4){
        $(".block1").css("position", 'relative');
        $(".block1").css("z-index", '3');
      }
      let posX = event.pageX;
      let posY = event.pageY;
      // console.log(event.target);
      // console.log(posX + ',' + posY)
      // // console.log(event.target);
      // event.dataTransfer.effectAllowed = 'copyMove';
      // event.dataTransfer.dropEffect = "copy";
      this.targetdiv = event.target;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      // console.log(this.distX + ',' + this.distY)
      this.selectnum = event.target.className;
      this.isAdded = false;
      this.isOnMove = true;//움직이고있다.
      // var selectedNum = this.selectnum;
      // var selectedNum = this.selectnum.split("block")[2].split(' ')[0]
      var selectedNum = m.num;
      // console.log(selectedNum);
      // if(Number(selectedNum)==7||Number(selectedNum)==8){
        // this.underfor.push({parentNum:this.resultStep.length,sonNum:0,x:posX + this.distX,y:posY + this.distY+45,overMe:false})
      this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false,forindex:this.fori,forson:-1});
        this.fori+=1;
      // }else if(Number(selectedNum)==8){
      //   this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 10 +'px',marginTop:posY + this.distY + 10 + 'px',class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false,ifindex:this.ifi,ifson:-1});
      //   this.ifi+=1;
      // }else{
      //   this.resultStep.push({num:Number(selectedNum),marginleft:'10px',marginTop:this.defaultStep[selectedNum].marginTop,class:'',overMe:'none',position:'absolute',index:this.resultStep.length,x:posX + this.distX,y:posY + this.distY,son:-1,onPlayBtn:false,loop:1,choiceNum:false});
      // }
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
       this.openStory2 = false;
       this.openStory2Start = false;
       this.openStory2End = false;
       if(this.stageType == 1) {
         this.onModal();
       } else if(this.stageType == 2 && this.openClear) {
         this.onModal();
       }
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
      // console.log(this.resultStep.length + '여기')
      var resultBlocknum=0;  //최종 블록 수
      var delNode = [];
      var forlist = [];
      this.code = [];
      this.resultmoves = [];
      // console.log(this.resultStep)
      while(tempson != -1){
        resultBlocknum +=1;
        this.code.push({move:this.resultStep[tempson],loop:this.resultStep[tempson].loop});
        // if(this.resultStep[tempson].num!=7){
          delNode.push(this.resultStep[tempson].index);
          if(this.resultStep[tempson].num==7){
            var tempforson = this.resultStep[tempson].forson;
            var ForresultString = String(this.resultStep[tempson].loop);
            while(tempforson !=-1){
              this.code.push({move:this.resultStep[tempforson],loop:this.resultStep[tempson].loop});
              resultBlocknum +=1;
              ForresultString+=',';
              // console.log(this.resultStep);
              // console.log(this.resultStep[tempforson]);
              // console.log("tempforson: "+tempforson);
              ForresultString += this.moves[this.resultStep[tempforson].num].move;
              if(this.resultStep[tempforson].num==8){
                ForresultString+='IfTrap';
                var tempifson = this.resultStep[tempforson].forson;
                while(tempifson!=-1){
                  ForresultString+=';';
                  // console.log(this.resultmoves[this.resultStep[tempifson].num]);
                  ForresultString+=this.moves[this.resultStep[tempifson].num].move;
                  tempifson = this.resultStep[tempifson].son;
                }
                tempforson = tempifson
                console.log(ForresultString);
                // this.result
              }else if(this.resultStep[tempforson].num!=7||this.resultStep[tempforson].son!=-1){
                tempforson = this.resultStep[tempforson].son;
              }else{
                tempforson = this.resultStep[tempforson].forson;
              }
            }
            this.resultStep[tempson].forindex = forlist.length;
            // // console.log(ForresultString);
            forlist.push(ForresultString);
          }
          this.resultmoves.push({move:this.resultStep[tempson],loop:this.resultStep[tempson].loop});
          tempson = this.resultStep[tempson].son;
          this.blockNum = resultBlocknum
      }

      // // console.log("resultStep["+0+"]="+this.resultStep[0].son);
      this.resultmoves.forEach( step => {
        if(step.move.num!=7){
          this.$refs.myInstance.message('JavascriptHook',this.moves[step.move.num].move);
          // // console.log(this.moves[step.move.num].move);
        }else{
          this.$refs.myInstance.message('JavascriptHook',this.moves[step.move.num].move,forlist[step.move.forindex]);
          // // console.log(this.moves[step.move.num].move+','+forlist[step.move.forindex]);
        }
      });
     for(var i=0; i<this.resultmoves.length;i++){
       this.history.push(this.resultmoves[i].move);
     }
    
     
     this.resultStep = [];
     this.playClass.background='#1dc360';
     this.$refs.myInstance.message('JavascriptHook',"Go");
    //  // console.log(delNode)
    //  for(var j=0; j<delNode.length;j++){
    //    this.resultStep.pop(j);
    //  }
      this.playson = -1;
      this.alreadyOverPlay = false;
      // // console.log(this.resultmoves);

      // 튜토리얼
      if(this.showTutorial == 6){
        this.showTutorial = 7
        $(".tutorial6").css('display', 'none')
        $(".block1").css("z-index", 'unset');
      }
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
      // console.log(store.state.kakaoUserInfo);
      if(this.hintCount > 0 && this.buyhint == false){
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
      // // console.log(x+','+y)
      // // console.log("underfor[0]"+this.underfor[0].x+" "+this.underfor[0].y)
      
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
      // // console.log(event.target);
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
      const LENG = this.blockNum
      if(this.stageType == 1) {
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
      } else if(this.stageType == 2) {
        if(this.stageNum == 1) {
          if(LENG <= 10) {this.starNum=3} else if(LENG > 10 && LENG <= 17) {this.starNum=2}
        } if(this.stageNum == 2) {
          if(LENG <= 9) {this.starNum=3} else if(LENG > 9 && LENG <= 11) {this.starNum=2}
        } if(this.stageNum == 3) {
          if(LENG <= 6) {this.starNum=3} else if(LENG > 6 && LENG <= 10) {this.starNum=2}
        } if(this.stageNum == 4) {
          if(LENG <= 7) {this.starNum=3} else if(LENG > 7 && LENG <= 10) {this.starNum=2}
        }
      }
      // axios
      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/stage/user`,{
        userId : store.state.kakaoUserInfo.id,
        stageId : this.stageType + "" + this.stageNum,
        star : this.starNum 
      });
    },
    drop(event) {
      // console.log(this.resultmoves)
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
          
        // // console.log(event);
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
          this.resultStep[this.targetdivNum].position = 'relative'; // 수정
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          if(!this.alreadyOverPlay){
              this.alreadyOverPlay = true;
            }
          // 튜토리얼 5
          if(this.showTutorial == 5 && this.stageType == 1 && this.stageNum == 1){
            var tempson2 = this.playson;
            var rightnum2 = this.resultStep[tempson2].num
            var blocknum2 = 0;
            while(tempson2 != -1 && rightnum2 == 1){
              blocknum2 += 1
              rightnum2 = this.resultStep[tempson2].num
              tempson2 = this.resultStep[tempson2].son
            }
          }
        }

        var content = window.document.getElementsByClassName("overMe");
        if(content.length!=0){
          this.resultStep[this.targetdivNum].marginleft = '0px';
          this.resultStep[this.targetdivNum].marginTop = '0px';
          this.resultStep[this.targetdivNum].position = 'unset';
          // console.log(content[0]);
          
        }
        // var original_son = -1;
        //x와 y를 움직이기

        this.resultStep.some( step => {
          if(step.overMe=='block' || step.class=='overMe'){
            if(step.num!=7 && step.num!=8){
                content[0].nextSibling.after(this.targetdiv);
                var os = this.resultStep[step.index].son;
                this.resultStep[step.index].son = this.targetdivNum;
                // this.resultStep[this.targetdivNum].son = os;
                var parent = step.index;
                var son = this.targetdivNum;
                var lastson = son;
                while(son != -1){
                  lastson = son;
                  this.resultStep[son].x = Number(this.resultStep[parent].x);
                  this.resultStep[son].y = this.resultStep[parent].y+47;
                  parent = son;
                  son = this.resultStep[son].son;
                }
                this.resultStep[lastson].son = os;
              }else{
                var underForblockParent = content[0].parentNode;
                var underForblock = underForblockParent.getElementsByClassName("underForblock")[0];
                // console.log(underForblock);
                if(!step.onclick){
                underForblock.nextSibling.after(this.targetdiv);
                os = this.resultStep[step.index].son;
                this.resultStep[step.index].son = this.targetdivNum;
                // this.resultStep[this.targetdivNum].son = os;
                parent = step.index;
                son = this.targetdivNum;
                 while(son != -1){
                    this.resultStep[son].x = Number(this.resultStep[parent].x);
                    this.resultStep[son].y = this.resultStep[parent].y+47;
                    parent = son;
                    son = this.resultStep[son].son;
                  }
                }else{
                  // console.log("여기여기")
                  underForblock.nextSibling.before(this.targetdiv);
                  os = this.resultStep[step.index].forson;
                  this.resultStep[step.index].forson = this.targetdivNum
                  this.resultStep[this.targetdivNum].forson = os;


                parent = step.index;
                  son = this.targetdivNum;
                  while(son != -1){
                  // console.log("p:"+parent+" s:"+son);
                    this.resultStep[son].x = Number(this.resultStep[parent].x);
                    this.resultStep[son].y = this.resultStep[parent].y+47;
                    parent = son;
                    son = this.resultStep[son].forson;
                  }

                }

              
          }
            // // console.log("원래"+step.index+"의 son "+this.resultmoves[step.index].son+"을 "+this.targetdivNum+"로 바꿈");
            // // console.log(this.targetdivNum+"의 son을"+os+"로 바꿈");
            step.overMe = 'none';
            // console.log()
          }
          if(step.overMe=='block' || step.class=='overMe')return;
          console.log(this.resultStep);
     });
    //  this.resultStep[this.targetdivNum].son = original_son;
    // console.log(this.resultStep);
     this.playClass.show='none';
      }
      // 튜토리얼 4
      if(this.showTutorial == 4) {
        for(var i=0; i<this.resultStep.length; i++){
          var rightblocks = [];
          var tempson = this.resultStep[i].son
          var rightnum = this.resultStep[i].num
          var blocknum = 1;
          rightblocks.push(this.resultStep[i])
          
          while(tempson != -1 && rightnum == 1){
            blocknum += 1
            rightblocks.push(this.resultStep[tempson])
            rightnum = this.resultStep[tempson].num
            tempson = this.resultStep[tempson].son
            if(blocknum == 4 && rightblocks.length == 4){
              rightblocks[0].class = 'rightblock'
              break
            }
          }
          if(blocknum == 4){
            break
          }
        }
      }
      
      if(this.showTutorial == 4 && blocknum == 4){
        this.showTutorial = 5
        $("#play").css("z-index", "4")
        $(".block1").css("z-index", "4")
        // $(".block-list").children(".block1").css("z-index", "unset")

      }
      // 튜토리얼 5
      if(this.showTutorial == 5 && blocknum2 == 4){
        this.showTutorial = 6
        $("#play").css("z-index", "5")
        $("#underplay").children().last().css("z-index", "5")
        $("#underplay").children().last().css("position", "relative")
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
      this.stageNum += 1;
      var stageInfo = this.$cookies.get('stageInfo');
      stageInfo.stageNum = this.stageNum;
      this.$cookies.set('stageInfo',stageInfo);

      if(this.stageType == 1){
        setTimeout(() => {
        location.reload()
        }, 100);
        this.$router.push('/speech');
      }else{
        this.openClear = false;
        this.openStory2 = true;
        this.openStory2Start = true;
        this.openStory2End = false;
        this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
        this.LevelLoad();
      }
    },
    handleStart() {
      setTimeout(() => {
        this.LevelLoad();
        this.loadMyCharacter();
      }, 10);
    },
    handleClear() {
      this.getStar();
      this.openClear = true;
      this.openStory = true;
      this.openStory2 = true;
      this.openStory2End = true;
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
      console.log(this.code);
      var code = [];
      var code_kor = [];
      var fornum = 0;
      var isFor = false;
      this.code.forEach(m => {
        if(fornum != m.loop && isFor){
          code.push("}");
          code_kor.push("}");
        }
        if(m.move.num==7){ // 반복문이 있을 때
          code.push("for (i = 0; i < "+ m.loop + "; i++) {");
          code_kor.push(this.moves[m.move.num].move_kor + "("+ m.loop + "번) {");
          fornum = m.loop;
          isFor = true;
        }else{ // 반복문이 아니면
          if(isFor){
            code.push("&ensp;"+this.moves[m.move.num].move + "();");
            code_kor.push("&ensp;"+this.moves[m.move.num].move_kor + "();");
          }else{
            code.push(this.moves[m.move.num].move + "();");
            code_kor.push(this.moves[m.move.num].move_kor + "();");
          }
        }
      });
      if(isFor){
        code.push("}");
        code_kor.push("}");
      }



      this.setCode(code)
      this.setCodeKor(code_kor)
      // console.log(code, '1');
      // console.log(code_kor, '2');

    },
    gostage(){
        this.$router.push('/gamemap')
        this.$cookies.set('reload', 'true');
      },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      // console.log("캐릭터 정보 불러오기");
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        // console.log(res);
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
    checkBlockArea() {
      if(this.stageType == 1) {
        this.isBlock1 = true
        this.isBlock2 = true
      } else if(this.stageType == 2) {
        this.isBlock1 = false
        if(this.stageNum < 3) {
          this.isBlock2 = true
        } else {
          this.isBlock2 = false
        }
      } 
    }
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

.block-box .block-mark-area {
  position: absolute;
  top: 280px;
  width: 100%; 
  height: 120px;
  background-color: #f5f5f5;
  border-right: 1px solid #dcdcdc94;
  z-index: 1;
}

.block-box .block-mark-area2 {
  position: absolute;
  top: 340px;
  width: 100%; 
  height: 60px;
  background-color: #f5f5f5;
  border-right: 1px solid #dcdcdc94;
  z-index: 1;
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
  cursor: pointer;
}

#historyBtn{
  display: inline-block;
  width: 25%;
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
  width: 25%;
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

#hint2{
    position: absolute;
    width: 40%;
    height: 0;
    top: 0;
    right: 40%;
    z-index: 2;
    background-color: white;
    /* padding: 107px 50px; */
    
}
#deleteAllBtn {
  display: inline-block;
  width: 25%;
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
  font-size: 20px;
  padding: 20px 5vw;
}

.script > h3 {
  margin-bottom: 5px;
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
.tutorial1 {
  position:fixed;
  top: 9.5%;
  left: 55%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 3;
  border-radius: 10px;
}
.balloon1 {
  position:relative;
  width:300px;
  height:120px;
  background:white;
  border-radius: 10px;
}
.balloon1:after {
  border-top: 0px solid transparent;
  border-left: 15px solid transparent;
  border-right: 15px solid transparent;
  border-bottom: 15px solid white;
  content: "";
  position: absolute;
  top: -15px;
  left: 140px;
}
.tutorialnextbtn {
  color: #ed2222;
  border: 2px solid #ed2222;
  float: right;
  padding: 1% 3%;
  border-radius: 15px;
  cursor: pointer;
}
.tutorialnextbtn:hover {
  background-color: #fc3f3f;
  color: white;
}
.tutorial2 {
  position:fixed;
  top: 9.5%;
  left: 65%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 3;
  border-radius: 10px;
}
.balloon2 {
  position:relative;
  width:350px;
  height:130px;
  background:white;
  border-radius: 10px;
}
.balloon2:after {
  border-top: 0px solid transparent;
  border-left: 15px solid transparent;
  border-right: 15px solid transparent;
  border-bottom: 15px solid white;
  content: "";
  position: absolute;
  top: -15px;
  left: 140px;
}
.tutorial3 {
  position:fixed;
  top: 9.5%;
  right: 1%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 3;
  border-radius: 10px;
}
.balloon3 {
  position:relative;
  width: 290px;
  height: 140px;
  background:white;
  border-radius: 10px;
}
.balloon3:after {
  border-top: 0px solid transparent;
  border-left: 15px solid transparent;
  border-right: 15px solid transparent;
  border-bottom: 15px solid white;
  content: "";
  position: absolute;
  top: -15px;
  right: 50px;
}
.tutorial4 {
  position:fixed;
  top: 10%;
  right: 41%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 2;
  border-radius: 10px;
}
.balloon4 {
  position:relative;
  width:350px;
  height:150px;
  background:white;
  border-radius: 10px;
}
.balloon4:after {
  border-top: 15px solid transparent;
  border-left: 15px solid white;
  border-right: 0px solid transparent;
  border-bottom: 15px solid transparent;
  content: "";
  position: absolute;
  top: 60px;
  left: 350px;
}
.balloon1text {
  padding: 5%;
  font-family: 'BMJUA';
}
.balloontext1 {
  font-size: 20px;
  color: red;
}
.balloontext2 {
  font-size: 16px;
  padding: 5% 0;
}
.showbtn {
  color: #ed2222;
  border: 2px solid #ed2222;
  float: right;
  padding: 1% 3%;
  border-radius: 15px;
  cursor: pointer;
}
.showbtn:hover {
  background-color: #fc3f3f;
  color: white;
}
.tutorial4btn {
  /* transform: translate(325%, 20%); */
  transition-property: all;
  transition-duration: 1s;
}
.tutorial5 {
  position:fixed;
  top: 2%;
  right: 21%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 4;
  border-radius: 10px;
}
.balloon5 {
  position:relative;
  width:350px;
  height:150px;
  background:white;
  border-radius: 10px;
}
.balloon5:after {
  border-top: 15px solid transparent;
  border-left: 15px solid white;
  border-right: 0px solid transparent;
  border-bottom: 15px solid transparent;
  content: "";
  position: absolute;
  top: 60px;
  left: 350px;
}
.tutorial6 {
  position:fixed;
  top: 2%;
  right: 21%;
  box-shadow : rgba(0,0,0,0.5) 0 0 0 9999px, rgba(0,0,0,0.5) 2px 2px 3px 3px;
  z-index : 5;
  border-radius: 10px;
}
.balloon6 {
  position:relative;
  width:350px;
  height:150px;
  background:white;
  border-radius: 10px;
}
.balloon6:after {
  border-top: 15px solid transparent;
  border-left: 15px solid white;
  border-right: 0px solid transparent;
  border-bottom: 15px solid transparent;
  content: "";
  position: absolute;
  top: 60px;
  left: 350px;
}
</style>
