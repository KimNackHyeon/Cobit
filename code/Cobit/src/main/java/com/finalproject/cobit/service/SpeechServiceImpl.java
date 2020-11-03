package com.finalproject.cobit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;import org.springframework.expression.spel.ast.RealLiteral;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

@Service
public class SpeechServiceImpl implements SpeechSerivce {

	@Override
	public List<String> analyzeText1(List<String> texts) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		List<String> tempList = new ArrayList<String>();
		tempList.add("앞으로 3칸 갔다가 왼쪽으로 회전해서 앞으로 내 칸 갑니다");
		for (String string : tempList) {
			KomoranResult analyzeResultList = komoran.analyze(string);
			String common = analyzeResultList.getPlainText();
			System.out.println(common);
			List<String> targetPosCollection = new ArrayList<String>();
			targetPosCollection.add("NNG");
			targetPosCollection.add("NP");
			targetPosCollection.add("NNP");
			targetPosCollection.add("SN");
			targetPosCollection.add("MM");
			List<String> list = analyzeResultList.getMorphesByTags(targetPosCollection);
			System.out.println(string+" "+list.toString());
		}

//        List<Token> tokenList = analyzeResultList.getTokenList();
//        for (Token token : tokenList) {
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//        }
		return null;
	}
//	private static String makeHanguel(String string) {
//		// TODO Auto-generated method stub
//		switch (string) {
//		case "NN":
//			return "명사";
//		case "NNP":
//			return "고유명사";
//		case "NNB":
//			return "의존명사";
//		case "NP":
//			return "대명사";
//		case "NR":
//			return "수사";
//		case "VV":
//			return "동사";
//		case "VA":
//			return "형용사";
//		case "VX":
//			return "보조용언";
//		case "VC":
//			return "지정사";
//		case "VCP":
//			return "긍정지정사";
//		case "VCN":
//			return "부정지정사";
//		case "MM":
//			return "관형사";
//		case "MA":
//			return "부사";
//		case "MAG":
//			return "일반부사";
//		case "MAJ":
//			return "접속부사";
//		case "IC":
//			return "감탄사";
//		case "JK":
//			return "격조사";
//		case "JKS":
//			return "주격조사";
//		case "JKC":
//			return "보격조사";
//		case "JKG":
//			return "관형격조사";
//		case "JKO":
//			return "목적격조사";
//		case "JKB":
//			return "부사격조사";
//		case "JKV":
//			return "호격조사";
//		case "JKQ":
//			return "인용격조사";
//		case "JX":
//			return "보조사";
//		case "JC":
//			return "접속조사";
//		case "E":
//			return "어미";
//		case "EP":
//			return "선어말어미";
//		case "EF":
//			return "종결어미";
//		case "EC":
//			return "연결어미";
//		case "ETN":
//			return "명사형전성어미";
//		case "ETM":
//			return "관형형전성어미";
//		case "XP":
//			return "접두사";
//		case "XPN":
//			return "체언접두사";
//		case "XS":
//			return "접미사";
//		case "XSN":
//			return "명사파생접미사";
//		case "XSV":
//			return "동사파생접미사";
//		case "XSA":
//			return "형용사파생접미사";
//		case "XR":
//			return "어근";
//		case "SF":
//			return "마침표,물음표,느낌표";
//		case "SP":
//			return "쉼표,가운뎃점,콜론,빗금";
//		case "SS":
//			return "따옴표,괄호표,줄표";
//		case "SE":
//			return "줄임표";
//		case "SO":
//			return "붙임표(물결,숨김,빠짐)";
//		case "SL":
//			return "외국어";
//		case "SH":
//			return "한자";
//		case "SW":
//			return "기타기호(논리수학기호,화폐기호)";
//		case "NF":
//			return "명사추정범주";
//		case "NV":
//			return "용언추정범주";
//		case "SN":
//			return "숫자";
//		case "NA":
//			return "분석불능범주";
//		default:
//			return "해석불가";
//		}
//	}
	
}
