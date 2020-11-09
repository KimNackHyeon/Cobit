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
//		List<String> temp = new ArrayList<String>();
//		temp.add("앞으로 네 칸 가기");
		List<String> commands = null;
		for (String string : texts) {
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
			if(commands == null) {
				commands = makeCommand(list);
			}else {
				commands.addAll(makeCommand(list));
			}
		}
		System.out.println(commands.toString());
		return commands;
	}

	private List<String> makeCommand(List<String> list) {
		List<String> result = new ArrayList<String>();
		int N = list.size();
		for(int i = 0; i < N; i++) {
			String command = list.get(i);
			if(command.contains("위")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					String next = list.get(i+1);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("up");
						}
						i++;
					}else if(nextCommand == '한') {
						result.add("up");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("up");
						}
						i++;
					}else if(nextCommand == '세') {
						for(int j = 0 ; j < 3; j++) {
							result.add("up");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("up");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("up");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("up");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("up");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("up");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("up");
						}
						i++;
					}
				}else {
					result.add("up");
				}
			}else if(command.contains("밑") || command.contains("아래")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					String next = list.get(i+1);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("down");
						}
						i++;
					}else if(nextCommand == '한') {
						result.add("down");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("down");
						}
						i++;
					}else if(nextCommand == '세') {
						for(int j = 0 ; j < 3; j++) {
							result.add("down");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("down");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("down");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("down");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("down");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("down");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("down");
						}
						i++;
					}
				}else {
					result.add("down");
				}
			}else if(command.contains("오른")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					String next = list.get(i+1);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("right");
						}
						i++;
					}else if(nextCommand == '한') {
						result.add("right");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("right");
						}
						i++;
					}else if(nextCommand == '세') {
						for(int j = 0 ; j < 3; j++) {
							result.add("right");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("right");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("right");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("right");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("right");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("right");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("right");
						}
						i++;
					}
				}else {
					result.add("right");
				}
			}else if(command.contains("왼")) {
				if(i != N-1) {
					char nextCommand = list.get(i+1).charAt(0);
					String next = list.get(i+1);
					if(nextCommand>='1' && nextCommand<='9') {
						int iter = nextCommand-'0';
						for(int j = 0; j < iter; j++) {
							result.add("left");
						}
						i++;
					}else if(nextCommand == '한') {
						result.add("left");
						i++;
					}else if(nextCommand == '두') {
						for(int j = 0 ; j < 2; j++) {
							result.add("left");
						}
						i++;
					}else if(nextCommand == '세') {
						for(int j = 0 ; j < 3; j++) {
							result.add("left");
						}
						i++;
					}else if(nextCommand == '네' || nextCommand == '내') {
						for(int j = 0 ; j < 4; j++) {
							result.add("left");
						}
						i++;
					}else if(next.contains("다섯")) {
						for(int j = 0 ; j < 5; j++) {
							result.add("left");
						}
						i++;
					}else if(next.contains("여섯")) {
						for(int j = 0 ; j < 6; j++) {
							result.add("left");
						}
						i++;
					}else if(next.contains("일곱")) {
						for(int j = 0 ; j < 7; j++) {
							result.add("left");
						}
						i++;
					}else if(next.contains("여덜") || next.contains("여덟")) {
						for(int j = 0 ; j < 8; j++) {
							result.add("left");
						}
						i++;
					}else if(next.contains("아홉")) {
						for(int j = 0 ; j < 9; j++) {
							result.add("left");
						}
						i++;
					}
				}else {
					result.add("left");
				}
			}
		}
		return result;
	}
	
}
