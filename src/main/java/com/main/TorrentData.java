package com.main;

public class TorrentData {
    private String peerId;
    private String tracker;
    private String infoHash;
    private String trackerResponse;
    private TorrentStats torrentStats;

    public TorrentData (String peerId, String tracker, String infoHash, String trackerResponse, TorrentStats torrentStats){
        this.peerId = peerId;
        this.tracker = tracker;
        this.infoHash = infoHash;
        this.trackerResponse = trackerResponse;
        this.torrentStats = torrentStats;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getTracker() {
        return tracker;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public String getTrackerResponse() {
        return trackerResponse;
    }

    public TorrentStats getTorrentStats() {
        return torrentStats;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    public void setTrackerResponse(String trackerResponse) {
        this.trackerResponse = trackerResponse;
    }

    public void setTorrentStats(TorrentStats torrentStats) {
        this.torrentStats = torrentStats;
    }

    @Override
    public String toString() {
        return "TorrentData{" +
                "peerId='" + peerId + '\'' +
                ", tracker='" + tracker + '\'' +
                ", infoHash='" + infoHash + '\'' +
                ", trackerResponse='" + trackerResponse + '\'' +
                ", torrentStats=" + torrentStats +
                '}';
    }
}
