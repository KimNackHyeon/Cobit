<template>
  <div class="wrap">
    <div class="map-container">
      <div class="map-header">
        <div class="back-btn"><i class="fas fa-chevron-left"></i>돌아가기</div>
      </div>
      <div class="map-body">
        <div class="map-stage-box" v-for="(inform, index) in mapInform" :key="`map+${index}`">
          <div class="stage-num">{{ index }}</div>
          <div v-if="inform.open" class="stage-area"></div>
          <div v-if="inform.open" class="stage-star">
            <i v-for="(star,idx) in inform.star" :key="`star+${index}+${idx}`" class="fas fa-star"></i>
            <i v-for="(unstar,idx) in inform.unstar" :key="`unstar+${index}+${idx}`" class="far fa-star"></i>
          </div>
          <div v-if="!inform.open" class="stage-area lock-stage-area"></div>
          <div v-if="!inform.open" class="stage-star lock-stage-star">
            <i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i>
          </div>
          <div class="map-road-box"></div>
          <div v-if="inform.user" class="map-character">
            <img src="../assets/images/penguin2.png" alt="">
          </div>
        </div>
      </div>
      <div class="map-footer">
        <i class="fas fa-star"></i>
        <div class="map-gauge">
          <div class="map-1star-gauge">★ {{ mapStar.first }}%</div>
          <div class="map-2star-gauge">★★ {{ mapStar.second }}%</div>
          <div class="map-3star-gauge">★★★ {{ mapStar.third }}%</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GameMap',
  data() {
    return {
      mapInform: {
        1: {
          open: true,
          star: 3,
          unstar: 0,
          user: false,
        },
        2: {
          open: true,
          star: 2,
          unstar: 1,
          user: false,
        },
        3: {
          open: true,
          star: 1,
          unstar: 2,
          user: false,
        },
        4: {
          open: true,
          star: 0,
          unstar: 3,
          user: true,
        },
        5: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        },
        6: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        },
        7: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        },
        8: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        },
        9: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        },
        10: {
          open: false,
          star: 0,
          unstar: 3,
          user: false,
        }
      },
      mapStar: {
        first: 33.3,
        second: 33.3,
        third: 0,
      }
    }
  },
  components: {
  },
  computed: {
  },
  created() {
    // window.addEventListener('scroll', this.handleScroll)
  },
  mounted() {
    this.getStarRatio();
  },
  watch: {
  },
  methods: {
    getStarRatio() {
      this.mapStar.third = (100 - this.mapStar.first - this.mapStar.second).toString().substring(0,4);
      const FIRST = document.querySelector('.map-1star-gauge');
      const SECOND = document.querySelector('.map-2star-gauge');
      const THIRD = document.querySelector('.map-3star-gauge');

      FIRST.style.width = this.mapStar.first + '%';
      SECOND.style.width = this.mapStar.second + '%';
      THIRD.style.width = this.mapStar.third + '%';
    }
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.map-container {
  margin-top: 50px;
  width: 100%;
  height: calc(100vh - 100px);
  /* background-color: bisque; */
}

.map-header .back-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 180px;
  height: 60px;
  background-color: #a4d4ff;
  font-size: 30px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
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
}

.stage-star .fas {
  color: rgb(243, 243, 0);
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
}

.stage-star .far {
  color: #000;
}

.stage-star i:nth-child(2) {
  margin-top: 5px;
}

.lock-stage-star i {
  color: transparent !important;
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
  .map-header .back-btn {
    width: 26vw;
    height: 8.5vw;
    font-size: 4.5vw;
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
}
</style>