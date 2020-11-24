package com.finalproject.cobit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

@Service
public class SpeechServiceImpl implements SpeechSerivce {

	@Override
	public List<String> analyzeText1(List<String> texts) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		List<String> commands = null;
		for (String string : texts) {
			KomoranResult analyzeResultList = komoran.analyze(string);
			String common = analyzeResultList.getPlainText();
			List<String> targetPosCollection = new ArrayList<String>();
			targetPosCollection.add("NNG");
			targetPosCollection.add("NP");
			targetPosCollection.add("NNP");
			targetPosCollection.add("SN");
			targetPosCollection.add("MM");
			targetPosCollection.add("XSN");
			targetPosCollection.add("XSV");
			targetPosCollection.add("NNB");
			targetPosCollection.add("JX");
			targetPosCollection.add("VV");
			targetPosCollection.add("NR");
			List<String> list = analyzeResultList.getMorphesByTags(targetPosCollection);
			System.out.println(list.toString());
			if(commands == null) {
				commands = makeCommand(list);
			}else {
				commands.addAll(makeCommand(list));
			}
		}
		return commands;
	}
	public static void main(String[] args) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		KomoranResult analyzeResultList = komoran.analyze("왼쪽으로 네 번가");//네: XSN 세: NNB 두:JX 하:VV	다섯: NR	
		String common = analyzeResultList.getPlainText();
		List<String> targetPosCollection = new ArrayList<String>();
		targetPosCollection.add("NNG");
		targetPosCollection.add("NP");
		targetPosCollection.add("NNP");
		targetPosCollection.add("SN");
		targetPosCollection.add("MM");
		targetPosCollection.add("XSN");
		targetPosCollection.add("XSV");
		targetPosCollection.add("NNB");
		targetPosCollection.add("JX");
		targetPosCollection.add("VV");
		targetPosCollection.add("NR");
		List<String> list = analyzeResultList.getMorphesByTags(targetPosCollection);
		System.out.println("-----------------------------------------------------");
		System.out.println();
		System.out.println("자연어        :   \"오른쪽으로세 번가\"");
		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
		System.out.println();
		System.out.println("형태소        :   \""+common);
		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
		System.out.println();
		System.out.println("키워드 추출    :   \""+list.toString());
		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
		System.out.println();
//		System.out.println("싫행 명령리스트 :	\""+makeCommand(list));
		System.out.println("-----------------------------------------------------");
		
		
	}

	private List<String> makeCommand(List<String> list) {
		List<String> result = new ArrayList<String>();
		int N = list.size();
		for(int i = 0; i < N; i++) {
			String command = list.get(i);
			if(command.contains("점프")) {
				result.add("Jump");
			}
			else if(command.contains("위")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					int k = i+1;
					while(nextCommand == '으' || nextCommand == '로') {
						nextCommand = list.get(++k).charAt(0);
						if(k == N)break;
					}
					if(k != N) {
						i = k;
					}
					String next = list.get(i);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("Up");
						}
						i++;
					}else if(nextCommand == '한' || nextCommand == '하') {
						result.add("Up");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("Up");
						}
						i++;
					}else if(nextCommand == '세' || nextCommand == '새') {
						for(int j = 0 ; j < 3; j++) {
							result.add("Up");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("Up");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("Up");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("Up");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("Up");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("Up");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("Up");
						}
						i++;
					}
				}else {
					result.add("Up");
				}
			}else if(command.contains("밑") || command.contains("아래")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					int k = i+1;
					while(nextCommand == '으' || nextCommand == '로') {
						nextCommand = list.get(++k).charAt(0);
						if(k == N)break;
					}
					if(k != N) {
						i = k;
					}
					String next = list.get(i);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("Down");
						}
						i++;
					}else if(nextCommand == '한' || nextCommand == '하') {
						result.add("Down");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("Down");
						}
						i++;
					}else if(nextCommand == '세' || nextCommand == '새') {
						for(int j = 0 ; j < 3; j++) {
							result.add("Down");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("Down");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("Down");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("Down");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("Down");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("Down");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("Down");
						}
						i++;
					}
				}else {
					result.add("Down");
				}
			}else if(command.contains("오른")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					int k = i+1;
					while(nextCommand == '으' || nextCommand == '로') {
						nextCommand = list.get(++k).charAt(0);
						if(k == N)break;
					}
					if(k != N) {
						i = k;
					}
					String next = list.get(i);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("Right");
						}
						i++;
					}else if(nextCommand == '한' || nextCommand == '하') {
						result.add("Right");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("Right");
						}
						i++;
					}else if(nextCommand == '세' || nextCommand == '새') {
						for(int j = 0 ; j < 3; j++) {
							result.add("Right");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("Right");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("Right");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("Right");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("Right");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("Right");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("Right");
						}
						i++;
					}
				}else {
					result.add("Right");
				}
			}else if(command.contains("왼")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					int k = i+1;
					while(nextCommand == '으' || nextCommand == '로') {
						nextCommand = list.get(++k).charAt(0);
						if(k == N)break;
					}
					if(k != N) {
						i = k;
					}
					String next = list.get(i);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("Left");
						}
						i++;
					}else if(nextCommand == '한' || nextCommand == '하') {
						result.add("Left");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("Left");
						}
						i++;
					}else if(nextCommand == '세' || nextCommand == '새') {
						for(int j = 0 ; j < 3; j++) {
							result.add("Left");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("Left");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("Left");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("Left");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("Left");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("Left");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("Left");
						}
						i++;
					}
				}else {
					result.add("Left");
				}
			}
		}
		return result;
	}
	
}