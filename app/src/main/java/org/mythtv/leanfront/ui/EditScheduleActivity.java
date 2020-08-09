/*
 * Copyright (c) 2016 The Android Open Source Project
 * Copyright (c) 2019-2020 Peter Bennett
 *
 * Incorporates code from "Android TV Samples"
 * <https://github.com/android/tv-samples>
 * Modified by Peter Bennett
 *
 * This file is part of MythTV-leanfront.
 *
 * MythTV-leanfront is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * MythTV-leanfront is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with MythTV-leanfront.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.mythtv.leanfront.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.GuidedStepSupportFragment;

import org.mythtv.leanfront.data.AsyncBackendCall;
import org.mythtv.leanfront.data.XmlNode;
import org.mythtv.leanfront.model.Video;

import java.util.Date;


public class EditScheduleActivity extends FragmentActivity implements AsyncBackendCall.OnBackendCallListener {

    private XmlNode mProgDetails;

    public static final String CHANID = "CHANID";
    public static final String STARTTIME = "STARTTIME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int chanId = getIntent().getIntExtra(EditScheduleActivity.CHANID, 0);
        Date startTime = (Date) getIntent().getSerializableExtra(EditScheduleActivity.STARTTIME);
        AsyncBackendCall call = new AsyncBackendCall(null, 0L, false,
                this);
        call.setId(chanId);
        call.setStartTime(startTime);
        call.execute(Video.ACTION_GETPROGRAMDETAILS);
    }

    @Override
    public void onPostExecute(AsyncBackendCall taskRunner) {
        int [] tasks = taskRunner.getTasks();
        switch (tasks[0]) {
            case Video.ACTION_GETPROGRAMDETAILS:
                mProgDetails = taskRunner.getXmlResult();
                if (mProgDetails != null) {
                    int recordId = Integer.parseInt(mProgDetails.getNode("Recording").getString("RecordId"));
                    if (recordId > 0) {
                        AsyncBackendCall call = new AsyncBackendCall(null, 0L, false,
                                this);
                        call.setId(recordId);
                        call.execute(Video.ACTION_GETRECORDSCHEDULE);
                    }
                    else
                        GuidedStepSupportFragment.addAsRoot(this,
                                new EditScheduleFragment(mProgDetails, null), android.R.id.content);
                }
                break;
            case Video.ACTION_GETRECORDSCHEDULE:
                XmlNode recordSchedule = taskRunner.getXmlResult();
                GuidedStepSupportFragment.addAsRoot(this,
                        new EditScheduleFragment(mProgDetails, recordSchedule), android.R.id.content);
                break;
        }

    }
}
