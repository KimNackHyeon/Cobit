export default {
  setStageDetail(state, data) {
    state.StageDetail = data
  },
  setStageNum(state, data) {
    state.StageNum = data
  },
  setKakaoUserInfo(state,kakaoLoginResult) {
    state.kakaoUserInfo.id = kakaoLoginResult.id;
    state.kakaoUserInfo.email = kakaoLoginResult.email;
    state.kakaoUserInfo.nickname = kakaoLoginResult.nickname;
    state.kakaoUserInfo.star = kakaoLoginResult.star;
  },
  delKakaouserInfo(state) {
    state.kakaoUserInfo.id = null;
    state.kakaoUserInfo.email = null;
    state.kakaoUserInfo.nickname = null;
    state.kakaoUserInfo.star = null;
  },
}