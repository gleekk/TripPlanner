package vo;

public class WeatherDTO {
	
	private String city;
	private String imgs;
	private String days;
	private String weathers;
	private String lows;
	private String highs;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getWeathers() {
		return weathers;
	}
	public void setWeathers(String weathers) {
		this.weathers = weathers;
	}
	public String getLows() {
		return lows;
	}
	public void setLows(String lows) {
		this.lows = lows;
	}
	public String getHighs() {
		return highs;
	}
	public void setHighs(String highs) {
		this.highs = highs;
	}
	public WeatherDTO(String city, String imgs, String days, String weathers, String lows, String highs) {
		super();
		this.city = city;
		this.imgs = imgs;
		this.days = days;
		this.weathers = weathers;
		this.lows = lows;
		this.highs = highs;
	}
	
	
	
	
}
