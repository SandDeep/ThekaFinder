package com.theka.search.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SolrData {
	@JsonProperty
	private int locId;
	@JsonProperty
	private String place;
	@JsonProperty
	private String address;
	@JsonProperty
	private String metadata;
	@JsonIgnore
	private String locNearBy;
	@JsonProperty
	private List<NearBy> nearByList;

	public static class NearBy {
		@JsonProperty
		private int nearLocId;
		@JsonProperty
		private String place;
		@JsonProperty
		private String address;
		@JsonProperty
		private String metadata;

		public int getNearLocId() {
			return nearLocId;
		}

		public void setNearLocId(int locId) {
			this.nearLocId = locId;
		}

		public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getMetadata() {
			return metadata;
		}

		public void setMetadata(String metadata) {
			this.metadata = metadata;
		}

		@Override
		public String toString() {
			return "NearBy [locId=" + nearLocId + ", place=" + place + ", address=" + address + ", metadata=" + metadata
					+ "]";
		}

	}

	public int getLocId() {
		return locId;
	}

	public void setLocId(int locId) {
		this.locId = locId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getLocNearBy() {
		return locNearBy;
	}

	public void setLocNearBy(String locNearBy) {
		this.locNearBy = locNearBy;
	}

	public List<NearBy> getNearByList() {
		return nearByList;
	}

	public void setNearByList(List<NearBy> nearByList) {
		this.nearByList = nearByList;
	}

	@Override
	public String toString() {
		return "SolrData [locId=" + locId + ", place=" + place + ", address=" + address + ", metadata=" + metadata
				+ ", locNearBy=" + locNearBy + ", nearByList=" + nearByList + "]";
	}
}
