package rogue_like;

public class Help {
	public static void mapIcons() {
		System.out.println("\n-----------------------------------------------------------------------------------------------");
		System.out.println("맵 아이콘 도움말");
		System.out.println("시작지점: " + Field.changeMapToSymbol(Numbers.START));
		System.out.println("도착지점: " + Field.changeMapToSymbol(Numbers.END));
		System.out.println("현재위치: " + Field.changeMapToSymbol(Numbers.PLAYER_POS));
		System.out.println("몬스터: " + Field.changeMapToSymbol(Numbers.MONSTER));
		System.out.println("상점: " + Field.changeMapToSymbol(Numbers.STORE));
		System.out.println("휴식처: " + Field.changeMapToSymbol(Numbers.SAFEHOUSE));
		System.out.println("길 : " + Field.changeMapToSymbol(Numbers.PATH));
		System.out.println("지나간 길 : " + Field.changeMapToSymbol(Numbers.PASSED));
		System.out.println("-----------------------------------------------------------------------------------------------");
	}
	
	public static void move() {
		System.out.println("\n-----------------------------------------------------------------------------------------------");
		System.out.println("이동 도움말");
		System.out.println("\"상 2\" -> 위쪽으로 2칸 이동 \"우 4\" -> 오른쪽으로 4칸 이동 ");
		System.out.println("-----------------------------------------------------------------------------------------------\n");
	}
}
