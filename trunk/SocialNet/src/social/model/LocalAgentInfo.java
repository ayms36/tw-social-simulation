package social.model;

import java.util.Random;

import jade.core.AID;

public class LocalAgentInfo {

	static Random random = new Random(System.nanoTime());

	AID aid;

	int lng, lat, lngR, latR;

	public int getRandomLng() {
		return lng + random.nextInt(lngR);
	}

	public int getRandomLat() {
		return lat + random.nextInt(lat);
	}

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}

	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLngR() {
		return lngR;
	}

	public void setLngR(int lngR) {
		this.lngR = lngR;
	}

	public int getLatR() {
		return latR;
	}

	public void setLatR(int latR) {
		this.latR = latR;
	}
}
