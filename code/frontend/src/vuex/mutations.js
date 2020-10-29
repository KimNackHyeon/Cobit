export default {
  setStageDetail(state, data) {
    state.StageDetail = data
  },
  setStageNum(state, data) {
    state.StageNum = data
  },
  setKakaoUserInfo(state,kakaoLoginResult) {
    state.kakaoUserInfo.email = kakaoLoginResult.email;
    state.kakaoUserInfo.nickname = kakaoLoginResult.nickname;
    state.kakaoUserInfo.star = kakaoLoginResult.star;
    // state.kakaoUserInfo.profile_image_url = kakaoLoginResult.profile_image_url;
  },
  delKakaouserInfo(state) {
    state.kakaoUserInfo.email = null;
    // state.kakaoUserInfo.profile_image_url = null;
    state.kakaoUserInfo.nickname = null;
    state.kakaoUserInfo.star = null;
  },
}