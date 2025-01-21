package com.main;

public class TorrentStats {
    private long uploaded = 0;
    private long downloaded = 0;
    private long left = -1;

    public void setLeft(long totalSize) {
        this.left = totalSize;
    }

    public synchronized void updateUploaded(long bytes){
        this.uploaded += bytes;
    }

    public synchronized void updateDownloaded (long bytes){
        this.downloaded += bytes;
        this.left -= bytes;
    }

    public String createTrackerUrl(String trackerUrl, String infoHash, String peerId) {
        return String.format(
                "%s?info_hash=%s&peer_id=%s&port=6881&uploaded=%d&downloaded=%d&left=%d&compact=1",
                trackerUrl,
                infoHash,
                peerId,
                uploaded,
                downloaded,
                left < 0 ? 0 : left  // Use 0 if size unknown
        );
    }

    @Override
    public String toString() {
        return "TorrentStats{" +
                "uploaded=" + uploaded +
                ", downloaded=" + downloaded +
                ", left=" + left +
                '}';
    }
}