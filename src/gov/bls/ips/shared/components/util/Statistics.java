/*******************************************************************************
 *
 * FILE : Statistics.java
 *
 * DESCRIPTION :
 * This class is essentially a data container holding statistics for Field Initiation.
 * Statistics that are maintained are:
 *    A. Total number of request
 *    B. Minimum time. This will not make sense for items whose statistics are
 *       not updated on a per-item basis; for example retrieval of activity items
 *    C. Maximum time. This will not make sense for items whose statistics are
 *       not updated on a per-item basis; for example retrieval of activity items
 *    D. Average time to process an item
 * All times are in milli-seconds.
 *
 *  REVISION HISTORY :
 *
 * Date                 Name            Release              Description
 * ------------  -----------------     ----------     ---------------------------------
 * 13Mar2012        Arun Jayavel      Iteration 10          original version
 * 13Mar2012        Arun Jayavel      Iteration 10          changed attribute names
 * 03/01/16	        Arun Jayavel      ICR Sprint 35      	Added current time
 * 
 * Copyright 2011 by All IPS-BLS .
 * All rights reserved.
 ******************************************************************************/
//Package assignment
package gov.bls.ips.shared.components.util;

//Non-BLS imports declarations.
import java.io.Serializable;

public class Statistics implements Serializable{

	private static final long serialVersionUID = 1L;
	private long _totalNumRequest;
	private long _minTime; // In milli-seconds.
	private long _maxTime; // In milli-seconds.
	private long _avgTime; // In milli-seconds and per item.
	private long _currentTime;

	public Statistics(){
		// Initialize.
		resetStats();
	}

	public long getNumItems(){
		return this._totalNumRequest;
	}

	public long getMinTime(){
		return this._minTime;
	}

	public long getMaxTime(){
		return this._maxTime;
	}

	public long getAvgTime(){
		return this._avgTime;
	}

	/**
	 * Updates statistics.
	 * @param newRequest New number request to add
	 * @param newProcessingTime New processing time
	 * @return None
	 */
	public synchronized void updateStats(long newRequest, long newTime){
		if (newRequest == 0)
			return;
		// Minimum time.
		if (newTime < this._minTime)
			this. _minTime = newTime;
		// Maximum time.
		if (newTime >this._maxTime)
			this._maxTime = newTime;
        
		// Average time.
		this._avgTime = ((this._totalNumRequest * this._avgTime) + newTime) / (this._totalNumRequest + newRequest);
		// Increment number items.
		this._totalNumRequest += newRequest;
		this._currentTime=newTime;
	}

	/**
	 * Updates statistics.
	 * @param newProcessingTime New processing time
	 * @return None
	 */
	public void updateStats(long newTime){
		updateStats(1, newTime);
	}

	/**
	 * Resets statistics.
	 * @return None
	 */
	public synchronized void resetStats(){
		this._totalNumRequest = 0;
		this._minTime = Long.MAX_VALUE;
		this._maxTime = Long.MIN_VALUE;
		this._currentTime=Long.MIN_VALUE;
		this._avgTime = 0;
	}

	/**
	 * Converts object to string.
	 * @return String representation of object
	 */
	public String toString(){
		return "# times = " + _totalNumRequest +
         ", currentTime = " + _currentTime +
		", minTime = " + _minTime +
		", maxTime = " + _maxTime +
		", avgTime = " + _avgTime;
	} 
}

