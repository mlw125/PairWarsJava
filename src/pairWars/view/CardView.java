package pairWars.view;

public class CardView {

	public String getCard(int suit, int face) {
		String card = "";
		
		switch(suit) {
		case 0:
			card += "\u2660";
			break;
		case 1:
			card += "\u2665";
			break;
		case 2:
			card += "\u2666";
			break;
		case 3:
			card += "\u2663";
			break;
		default:
			break;
		}

		switch(face) {
		case 1:
			card += "A";
			break;
		case 2:
			card += "2";
			break;
		case 3:
			card += "3";
			break;
		case 4:
			card += "4";
			break;
		case 5:
			card += "5";
			break;
		case 6:
			card += "6";
			break;
		case 7:
			card += "7";
			break;
		case 8:
			card += "8";
			break;
		case 9:
			card += "9";
			break;
		case 10:
			card += "10";
			break;
		case 11:
			card += "J";
			break;
		case 12:
			card += "Q";
			break;
		case 13:
			card += "K";
			break;
		}
		
		return card;
	}
}
