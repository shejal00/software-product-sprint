// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.lang.Math;
import java.util.HashSet;

public final class FindMeetingQuery {

    /**
    * Finds the intervals available for the meeting request.
    * @param events  collection of events happening excluding meeting request.
    * @param request the meeting request for which available time intervals have to be calculated.
    * @return        available intervals for the meeting request.
    */
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

        //Merged intervals(disjoint) clashing with the meeting request attendees.
        List<TimeRange> clashingIntervals = findClashingIntervalsForAllAttendees(events, request);

        //Duration of the meeting request. 
        long duration = request.getDuration();

        //Available intervals for the meeting request.
        Collection<TimeRange> availableIntervals = findAvailableIntervalsForRequest(clashingIntervals, duration);

        return availableIntervals;
    } 

    /**
    * Finds the list of intervals clashing with the meeting request attendees by ignoring the intervals 
    * that share no common attendees between the meeting request and the other event in comparison. 
    * @param events  collection of events happening excluding meeting request.
    * @param request the meeting request for which available time intervals have to be calculated.
    * @return        list of intervals that are clashing with meeting request attendees.
    */
    private List<TimeRange> ignoreIntervalsWithNoCommonAttendees(Collection<Event> events, MeetingRequest request) {

        //Collection of attendees attending the meeting request.
        Collection<String> attendeesCollection = request.getAttendees();
        Set<String> attendees = new HashSet<String>(attendeesCollection);

        //List of intervals that are clashing with meeting request attendees.
        List<TimeRange> allIntervals = new ArrayList<>(); 

        //Iterate over all events to find if someone from meeting request is involved in the other event.
        for(Event event: events) {
            if(Collections.disjoint(event.getAttendees(), attendees) == false) {
                allIntervals.add(event.getWhen());
            } 
        }

        return allIntervals;
    }

    /**
    * Merges the overlapping intervals from the list of intervals that are clashing with meeting request attendees.
    * @param events  collection of events happening excluding meeting request.
    * @param request the meeting request for which available time intervals have to be calculated.
    * @return        merged intervals(disjoint) clashing with the meeting request attendees.
    */
    private List<TimeRange> findClashingIntervalsForAllAttendees(Collection<Event> events, MeetingRequest request) { 

        //List of intervals that are clashing with meeting request attendees.
        List<TimeRange> allIntervals = ignoreIntervalsWithNoCommonAttendees(events, request);

        //Sorts the clashing intervals by their start time in ascending order. 
        Collections.sort(allIntervals, TimeRange.ORDER_BY_START);
        int currentIntervalStart = -1;
        int currentIntervalEnd = -1;

        //Merged intervals(disjoint) clashing with the meeting request attendees.
        List<TimeRange> clashingIntervals = new ArrayList<>();

        //Iterate over allIntervals to find the overlapping intervals.
        for(TimeRange eventTimeRange: allIntervals) {
            //If this is the first interval, intialze currentIntervalStart and currentIntervalEnd.
            if(currentIntervalStart == -1) {
                currentIntervalStart = eventTimeRange.start();
                currentIntervalEnd = eventTimeRange.end();
            } else if(currentIntervalEnd <= eventTimeRange.start()) {
                TimeRange newClashingInterval = TimeRange.fromStartEnd(currentIntervalStart, currentIntervalEnd, false);
                clashingIntervals.add(newClashingInterval);
                //Reset the values of currentIntervalStart and currentIntervalEnd.
                currentIntervalStart = eventTimeRange.start();
                currentIntervalEnd = eventTimeRange.end();
            } else {
                currentIntervalEnd = Math.max(currentIntervalEnd, eventTimeRange.end());
            }
        }
        //Adds the last interval non-overlapping interval(if it exists) into clashingIntervals
        if(currentIntervalStart != -1) {
            TimeRange newClashingInterval = TimeRange.fromStartEnd(currentIntervalStart, currentIntervalEnd, false);
            clashingIntervals.add(newClashingInterval);
        }

        return clashingIntervals;
    }

    /**
    * Finds the intervals available for the meeting request by finding the gaps larger than the meeting request duration.
    * @param clashingIntervals merged intervals(disjoint) clashing with the meeting request attendees.
    * @param duration          duration of the meeting request for which available time intervals have to be calculated.
    * @return                  available intervals for the meeting request.
    */
    private Collection<TimeRange> findAvailableIntervalsForRequest(List<TimeRange> clashingIntervals, long duration) {
        
        //Available intervals for the meeting request.
        Collection<TimeRange> availableIntervals = new ArrayList<>();

        //If there is no clashing in intervals then the meeting request can take any slot from the whole day.
        if(clashingIntervals.size() == 0) {
            if(duration < TimeRange.WHOLE_DAY.duration()) {
                availableIntervals.add(TimeRange.WHOLE_DAY);
            }        
        } else {
            //Checks the time interval available between start of day and the first clashingInterval's start time.
            if(clashingIntervals.get(0).start() - TimeRange.START_OF_DAY - 1 >= duration) {
                TimeRange newAvailableInterval = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, clashingIntervals.get(0).start(), false);
                availableIntervals.add(newAvailableInterval);
            }
            //Checks for the time intervals available between consecutive clashingIntervals.
            for(int i = 1; i < clashingIntervals.size(); i++) {
                if(clashingIntervals.get(i).start() - clashingIntervals.get(i-1).end() >= duration) {
                    TimeRange newAvailableInterval = TimeRange.fromStartEnd(clashingIntervals.get(i-1).end(), clashingIntervals.get(i).start(), false);
                    availableIntervals.add(newAvailableInterval);
                }
            }
            //Checks the time interval available between last clashingIntervals's end time and end of day.
            if(TimeRange.END_OF_DAY - clashingIntervals.get(clashingIntervals.size()-1).end() + 1 >= duration) {
                TimeRange newAvailableInterval = TimeRange.fromStartEnd(clashingIntervals.get(clashingIntervals.size()-1).end(), TimeRange.END_OF_DAY, true);
                availableIntervals.add(newAvailableInterval);
            }
        } 

        return availableIntervals;
    }
}
