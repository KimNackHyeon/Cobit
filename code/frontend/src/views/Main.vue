<template>
  <div class="main">
    <!-- <div class="navbar">Navbar</div> -->
    <div class="mainbox">
      <v-carousel hide-delimiters style="height: 100vh">
        <v-carousel-item>
          <div class="mainitem">
            <div class="onecontent">
              <!-- <div class="title">cobit</div> -->
              <img class="cobit" src="../assets/images/cobit.png" alt="logo">
              <div class="subtitle">코딩 습관을 길러주는 cobit</div>
              <div class="subtitlecontent">
                cobit은 학생들이 쉽고 재미있게 코딩을 학습할 수 있게 하여<br> 코딩 습관을 기르는데 도움을 줍니다.<br>
                학생들은 각 스테이지의 학습목표에 따라 코딩 핵심 개념을 배울 수 있습니다.
              </div>
              <img class="main1img" src="../assets/images/main1.png" alt="mainImg">
            </div>
          </div>
        </v-carousel-item>
        <v-carousel-item>
          <div class="mainitem">
            <div class="twomainleft">
              <div class="twomaintitle">키보드 방향키로 목적지에 도달하는 게임을 해본적이 있나요??</div>
              <img src="../assets/images/directionkey.png" alt="directionkey">
            </div>
            <div class="twomainright">
              <img src="../assets/images/main2.png" alt="map">
            </div>
          </div>
        </v-carousel-item>
        <v-carousel-item>
          <div class="mainitem">
            <div class="twomainleft">
              <div class="twomaintitle">cobit은 블록코딩을 통해<br> 논리적 사고를 기를 수 있습니다.</div>
              <br>
              <br>
              <img src="../assets/images/block.png" alt="block">
            </div>
            <div class="twomainright">
              <img src="../assets/images/main3.png" alt="map">
            </div>
          </div>
        </v-carousel-item>
        <v-carousel-item>
          <div class="mainitem">
            <div class="fourcontent">
              <div class="fourmaintitle">각 스테이지에 있는 학습목표에 따라<br> 코딩개념을 학습할 수 있습니다.</div>
              <div style="margin-bottom: 5%;">
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-arrow-down-bold</v-icon>
                  <div class="downarrowtitle">순차</div>
                </div>
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-cached</v-icon>
                  <div class="downarrowtitle">반복</div>
                </div>
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-call-split</v-icon>
                  <div class="downarrowtitle">조건</div>
                </div>
              </div>
              <div>
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-chevron-right</v-icon>
                  <div class="downarrowtitle">연산자</div>
                </div>
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-code-braces</v-icon>
                  <div class="downarrowtitle">함수</div>
                </div>
                <div class="oneicon">
                  <v-icon class="downarrow">mdi-code-parentheses</v-icon>
                  <div class="downarrowtitle">매개변수</div>
                </div>
              </div>
            </div>
          </div>
        </v-carousel-item>
        <v-carousel-item>
          <div class="mainitem">
            <div class="twomainleft">
              <div class="twomaintitle">바이러스를 물리치는 스토리를<br> 통해 컴퓨터 바이러스 지식을<br> 배울 수 있습니다.</div>
            </div>
            <div class="twomainright">
              <img src="../assets/images/computervirus.png" alt="computervirus">
            </div>
          </div>
        </v-carousel-item>
        <v-carousel-item>
          <div class="mainitem">
              <div class="lastcontent">
                <div class="twomaintitle">나만의 캐릭터를 만들어 함께 모험을 떠나볼까요?</div>
                <img src="../assets/images/mycharacter.png" alt="mycharacter">
                <div class="gohomebtnbox">
                  <div class="gohomebtn">모험하기</div>
                </div>
              </div>
          </div>
        </v-carousel-item>
      </v-carousel>
    </div>
  </div>
</template>

<script>
import '../css/main.scss'
export default {

}
</script>

<style scoped>
.main {
  font-family: 'Noto Sans KR';
  height: 100%;
}
.navbar {
  height: 7vh;
  text-align: center;
}
.mainbox {
  height: 100%;
  overflow-y: scroll;
}
.mainitem {
  background: linear-gradient( to bottom, #a4d4ff, white );
  height: 100vh;
  overflow: hidden;
}
.onecontent {
  height: 100%;
  padding: 0 20%;
  text-align: center;
}
.main1img {
  height: 45%;
}
.title {
  font-size: 100px;
}
.subtitle {
  font-size: 36px;
  font-weight: 600;
  padding-bottom: 3%;
}
.subtitlecontent {
  font-size: 17px;
  padding-bottom: 3%;
}
.cobit {
  height: 20%;
  margin: 3% 0;
}
.twomainleft {
  width: 50%;
  height: 100%;
  float: left;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 3% 0 10%;
  flex-direction: column;
}
.twomainright {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 5% 0 0;
}
.twomainleft img {
  width: 60%;
}
.twomainright img {
  width: 90%;
}
.twomaintitle {
  font-size: 28px;
}
.fourcontent {
  height: 100%;
  padding: 0 20%;
  text-align: center;
}
.fourmaintitle {
  margin: 20vh 10vh 10vh;
  font-size: 28px;
}
.oneicon {
  display: inline-block;
  width: 30%;
}
.downarrow {
  font-size: 80px;
  color: black;
}
.downarrowtitle {
  font-size: 22px;
}
.lastcontent {
  padding: 13vh 20% 0;
  text-align: center;
}
.gohomebtnbox {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 5vh;
}
.gohomebtn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 60px;
  background-color: #a4d4ff;
  font-size: 26px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}
.gohomebtn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
</style>