package rogue_like;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MapHandler {
	static Map[] maps;
	
	//생성자 호출시 바로 맵 읽어서 maps에 저장
	MapHandler() {
		//isValid = false인 맵까지 전부 로드 되기 때문에 주의!
		maps = loadMaps();
	}
	
	
	public static Map[] loadMaps(){
    	File mapRoute = new File(Numbers.MAP_FILES_ROUTE);
    	
    	//폴더가 존재 하지 않으면 만들기
    	if (!(mapRoute.isDirectory())) {
    		System.out.println(mapRoute.getName() + " 폴더가 만들어 졌습니다.");//<-----임시 확인용 구문
    		mapRoute.mkdir();
    	}
    	
    	//maps 폴더에 있는 파일들을 읽어서 files에 저장 후 이름만 가져와 map_list에 저장
    	File[] files = mapRoute.listFiles();
    	String[] map_list = new String[files.length];
    	for (int i=0; i<files.length; i++) {
    		map_list[i] = files[i].getName();
    	}
     
        Map[] maps = new Map[map_list.length];

        for(int i = 0; i < maps.length; i++){
            maps[i] = loadMap(map_list[i]);
        }
        return maps;
    }
	
	/*
     * @param fileName: 파일 이름 (오직 파일 이름만 있음. 루트 따로 지정 필요)
     * 인자로 받은 fileName에 대해서 파일을 읽은 후 유효성을 검사 후에 map 반환
     * 유효하지 않으면 isValid에 대해 default value인 false 값을 가지고 있는 map 바로 반환
     */
    public static Map loadMap(String fileName){
    	Map map = new Map(fileName); //임시로 객체 생성 내용 변경 후 반환

        File mapFile = new File(Numbers.MAP_FILES_ROUTE + fileName);      //맵을 읽어옴 *fileName에 결로를 추가하였음

        //이 구문이 여기서 필요한지는 모르지만 일단 한번 더 파일 있는지 확인해주는 구문
        if (!mapFile.exists()) {
        	System.out.println(fileName + "이 존재하지 않습니다. 건너뜁니다...");
        	//존재하지 않을 시에 isValid에 대해 default value인 false 값을 가지고 있는 map 바로 반환
			map.setErrorMessage("해당 맵이 존재 하지 않습니다. : " + map.getMapName());
        	return map;
        }
        
        BufferedReader br = null;
        String[][] input = null; //맵 내용 저장
        //유효성 검사를 위한 임시 변수
        try {
        	//UTF-8로 읽는 형식 통일 
        	br = new BufferedReader(new InputStreamReader(
					new FileInputStream(mapFile), Charset.forName("UTF8")));
        	
        	String line = ""; //한 줄 읽어서 저장할 변수
        	StringTokenizer st = null; //첫줄을 읽고 분해 위한 객체
        	
        	/*
             * 첫 줄 : (monsterNum)/(safehouseNum)/(storeNum)/(randomNum)
             * 첫 줄을 읽고 나서 StringTokenizer를 이용해 각 변수에 저장
             * */
        	line = br.readLine().trim();
        	//첫번 째 줄 형식 맞는지 확인
        	if (!line.matches(Numbers.FIRST_LINE_REGEX)) {
        		System.out.println("파일 내용 오류 - 1st line : " + line);
        		map.setErrorMessage("파일 첫 줄 오류");
        		return map;
        	}
        	st = new StringTokenizer(line, Numbers.MAP_CONTENTS_DELIM);
        	
        	//토큰 순서대로 대입
			map.setMonsterNum(Integer.parseInt(st.nextToken().trim()));
			map.setSafehouseNum(Integer.parseInt(st.nextToken().trim()));
			map.setStoreNum(Integer.parseInt(st.nextToken().trim()));
			map.setRandomNum(Integer.parseInt(st.nextToken().trim()));
        	
        	/*
        	 * 두번째 줄 ~ : 맵의 내용을 읽기
        	 * 다음 라인이 없을 때 까지 계속 읽음
        	 * */
        	ArrayList<String> temp_map = new ArrayList<>();
        	while ((line = br.readLine()) != null) {
        		//내용 확인
        		if (!line.matches(Numbers.MAP_LINE_REGEX)) {
        			System.out.println("맵 구성 라인이 잘못되었습니다 : " + line);
        			map.setErrorMessage("맵 구성이 잘못되었습니다.");
        			return map;
        		}
        		temp_map.add(line);
        		
        	}
        	
        	//첫줄의 글자 수 만큼의 column을 가진 배열 생성
        	input = new String[temp_map.size()][temp_map.get(0).length()];
        	
        	/*
        	 * 파일에 있는 맵이 직사각형이 아닐 때
        	 * 이 부분에서  ArrayIndexOutOfBoundsException 발생 할 수 있음
        	 * (물론 반대로 길이가 부족해서 0으로 채워 질 수도 있음)
        	 * 이 부분은 후에 isRect 함수와 동일하게 적용 될 수 있음...
        	 * 2번 확인하는 것이 안된다면 파일을 다르게 읽는 방법을 찾아야됨
        	 */
        	//input에 대입
        	try {
        		for (int i=0; i<input.length; i++) {
            		for (int j=0; j<input[i].length; j++) {
            			input[i][j] = Character.toString(temp_map.get(i).charAt(j));
            		}
            	}
        	} catch (IndexOutOfBoundsException e) {
        		map.setErrorMessage("맵에는 0~9까지 숫자만 올 수 있습니다");
            	System.out.println(map.getErrorMessage());
            	return map;
        	}
        	
        }
        catch (Exception e) {
        	//오류 발생 시에 오류 출력 후 null값 반환 
        	System.out.println(e.getMessage()); //<-------- 오류 확인 위한 임시 구문
			map.setErrorMessage("에러: " + e.getMessage());
        	return map;
        } finally {
        	if (br != null) {
        		try {
        			br.close();
        		}
        		catch (IOException e) {
        		}
        	}
        }
        
        /*
        여기서 맵에 대한 로직을 처리해 맵의 정보를 가져옴. 만약 맵이 잘못된 경우 isValid = false;
        -> 나중에 게임 화면에서 해당 맵도 load는 하되 선택할 수 없게 하고, 이유를 출력하도록 해야 함
        ---> 어떻게 잘못된 파일인걸 판단하냐고? 여기서 false로 출력되면 바로 얄짤없이 버리는거로 함.
        난이도는 나중에 게임에서 설정 가능하도록
         */
        
        //유효성 검사
		map.setValid(validate(input, map));
        if (!map.getValid()) { //메소드를 static으로 호출하면 errormessage가 적용이 안돼서 nonstatic으로 수정했습니다
        	System.out.println("map is not valid : " + map.getMapName()); //<----임시 구문
        	System.out.println(map.getErrorMessage());
        	return map;
        }
        
        //검사 통과 후 맵에 대입
		map.setMap(intCast(input));
        
        /*
         * 맵이 완전히 유효 (시작과 끝이 있는 맵인지, entity들이 지정된 비율 내에 있는지...등등)한지
         * 확인 후에 맵의 PATH에 monster, store, safehouse를 배치
         */
        setEntity(map, Numbers.MONSTER);
        setEntity(map, Numbers.SAFEHOUSE);
        setEntity(map, Numbers.STORE);
        
        
        return map;
    }
    
    private static void setEntity(Map map, int entity) {
		// TODO Auto-generated method stub
		int r = 0;
		int c = 0;
		int count = 0;
		
		switch (entity) {
		case Numbers.MONSTER:
			count = map.getMonsterNum();
			break;
		case Numbers.SAFEHOUSE:
			count = map.getSafehouseNum();
			break;
		case Numbers.STORE:
			count = map.getStoreNum();
			break;
		default:
			//nothing
			break;
		}
		
		//개수가 잘못되면 무한 루프로 갈 수 있으므로 반드시 맵 형식 확인한 후에 사용
		for (int i=0; i<count; i++) {
			r = getRandomInt(map.getMapRow() - 1, 0);
			c = getRandomInt(map.getMapCol() - 1, 0);
			
			if (map.getSpecificLocation(r, c) == Numbers.PATH) {
				map.setSpecificLocation(r, c, entity);
			}
			else {
				i--;
			}
		}
    }
    
    private static int getRandomInt(int max, int min) {
		return (int)(Math.random() * max) + min;
	}

	//맵 검사 후 Field에서 불러올때 유효성을 검사하도록 하면 된다.
	//여기서 이렇게 다 처리해줄 필요가 없음
	public static boolean validate(String[][] stringMap, Map map) {
    	if (!Checker.isRect(stringMap)) {
    		map.setErrorMessage("맵이 사각형꼴이 아닙니다");
        	System.out.println(map.getErrorMessage());
        	return false;
    	}
        else if(!Checker.isSingleDigitInt(stringMap)) {
        	map.setErrorMessage("맵에는 0~9까지 숫자만 올 수 있습니다");
        	System.out.println(map.getErrorMessage());
        	return false;
        }
        else if(!Checker.no0sInEdge(intCast(stringMap))) {
        	map.setErrorMessage("맵 가장자리에는 0이 올 수 없습니다");
        	System.out.println(map.getErrorMessage());
        	return false;
        }
        else if(!Checker.isReachable(intCast(stringMap))) {
        	map.setErrorMessage("시작점에서 끝까지 갈 수 없거나, 접근할 수 없는 공간(0)이 있습니다");
        	System.out.println(map.getErrorMessage());
        	return false;
        }
    	
    	//모든 테스트 통과 후 true 반환
    	return true;
    }

    
    private static int[][] intCast(String[][] stringMap) { //String 배열에서 int 배열로 형변환
    	int[][] intMap = new int[stringMap.length][stringMap[0].length];
    	for(int i = 0; i < stringMap.length; i++) {
    		for(int j = 0; j < stringMap[i].length; j++) {
    			intMap[i][j] = Integer.parseInt(stringMap[i][j]); 
    		}
    	}
    	return intMap;
    }
	
	public Map[] getMaps() {
		return MapHandler.maps;
	}
}