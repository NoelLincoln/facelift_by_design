package com.facelift.review.vote;

public enum VoteType {
	UP {
		public String toString() { return "up"; }
	},
	
	DOWN {
		public String toString() { return "down"; }
	}
}
