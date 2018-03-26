package iloveyesterday.mobile.pojo;

public class Region {
    private Double regionId;

    private String regionCode;

    private String regionName;

    private Double parentId;

    private Double regionLevel;

    private Double regionOrder;

    private String regionNameEn;

    private String regionShortnameEn;

    public Region(Double regionId, String regionCode, String regionName, Double parentId, Double regionLevel, Double regionOrder, String regionNameEn, String regionShortnameEn) {
        this.regionId = regionId;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.parentId = parentId;
        this.regionLevel = regionLevel;
        this.regionOrder = regionOrder;
        this.regionNameEn = regionNameEn;
        this.regionShortnameEn = regionShortnameEn;
    }

    public Region() {
        super();
    }

    public Double getRegionId() {
        return regionId;
    }

    public void setRegionId(Double regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public Double getParentId() {
        return parentId;
    }

    public void setParentId(Double parentId) {
        this.parentId = parentId;
    }

    public Double getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Double regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Double getRegionOrder() {
        return regionOrder;
    }

    public void setRegionOrder(Double regionOrder) {
        this.regionOrder = regionOrder;
    }

    public String getRegionNameEn() {
        return regionNameEn;
    }

    public void setRegionNameEn(String regionNameEn) {
        this.regionNameEn = regionNameEn == null ? null : regionNameEn.trim();
    }

    public String getRegionShortnameEn() {
        return regionShortnameEn;
    }

    public void setRegionShortnameEn(String regionShortnameEn) {
        this.regionShortnameEn = regionShortnameEn == null ? null : regionShortnameEn.trim();
    }
}