package com.amsort.processSim.process.requests;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Random;

public final class TrackId implements Comparable<TrackId> {

	private short trackId;

	public void setTrackId(short trackId) {
		this.trackId = trackId;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public TrackId() {
	}

	@JsonIgnore
	private String string;

	public TrackId(short trackId) {
		this.trackId = trackId;
		this.string = String.format("TrackID %d (0x%04x)", trackId, trackId & 0xFFFF);
	}

	public TrackId(int trackId) {
		this((short) trackId);
	}

	public TrackId(byte[] buffer, int pos) {
		this((buffer[pos+1] & 0xFF) | (buffer[pos] << 8));
	}

	public TrackId(String string) {
		this(Short.parseShort(string));
	}

	public final void writeTo(byte[] buffer, int pos) {
		buffer[pos] = (byte)((trackId >> 8) & 0xFF);
		buffer[pos + 1] = (byte)((trackId) & 0xFF);	
	}

	public final TrackId nextTrackId() {
		if (trackId <=0 || trackId>0x7FFF) {
			throw new IllegalArgumentException("Cannot get next id for invalid trackId");
		}
		if (trackId == 0x7FFF) return new TrackId(1);
		else return new TrackId(trackId + 1);
	}
	
	public final int getTrackId() {
		return trackId;
	}
	@JsonIgnore
	
	public final boolean isValid() {
		return trackId > 0 && trackId <= 0x7FFF;
	}
	@JsonIgnore
	public final boolean isMinusOne() {
		return trackId == -1 || trackId == 0xFFFF;
	}

	@JsonIgnore
	public final boolean isZero() {
		return trackId == 0;
	}
	@JsonIgnore

	public final boolean divisable(int by) {
		return trackId % by == 0;
	}

	@JsonIgnore
	public final static TrackId MINUSONE = new TrackId(-1);
	@JsonIgnore
	public final static TrackId ZERO = new TrackId(0);
	
	public final static TrackId random() {
		int i = rng.nextInt() & 0x7FFF;
		return new TrackId(i);
	}

	@JsonIgnore
	private final static Random rng = new Random();
	
	@Override
	public final String toString() {
		return string;
	}

	@Override
	public int compareTo(TrackId other) {
		if (this.trackId < other.trackId)
			return -1;
		if (this.trackId > other.trackId)
			return +1;
		return 0;
	}

	@Override
	public int hashCode() {
		return TrackId.class.hashCode()^(trackId&0xFFFF)^((trackId&0xFFFF) << 16);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackId other = (TrackId) obj;
		if (trackId != other.trackId)
			return false;
		return true;
	}
}
