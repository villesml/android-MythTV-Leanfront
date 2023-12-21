/*
 * Copyright (c) 2019-2020 Peter Bennett
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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;

import org.mythtv.leanfront.R;
import org.mythtv.leanfront.model.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsEntryFragment extends GuidedStepSupportFragment {
//        implements AsyncBackendCall.OnBackendCallListener {

    // For multiple occurrence items (e.g. playback groups),
    // add 100, 200, 300 etc to the number
    private static final int ID_BACKEND_IP = 1;
    private static final int ID_HTTP_PORT = 2;
    private static final int ID_BOOKMARK_LOCAL = 5;
    private static final int ID_PLAYBACK = 7;
    private static final int ID_SKIP_FWD = 8;
    private static final int ID_SKIP_BACK = 9;
    private static final int ID_JUMP = 10;
    private static final int ID_PROG_LIST_OPTIONS = 11;
    private static final int ID_SORT_ORIG_AIRDATE = 12;
    private static final int ID_DESCENDING = 13;
    private static final int ID_BACKEND_MAC = 14;
    private static final int ID_BACKEND = 15;
    private static final int ID_AUDIO = 16;
    private static final int ID_AUDIO_AUTO = 17;
    private static final int ID_AUDIO_MEDIACODEC = 18;
    private static final int ID_AUDIO_FFMPEG = 19;
    private static final int ID_LIVETV_DURATION = 21;
    private static final int ID_FRAMERATE_MATCH = 22;
    private static final int ID_SUBTITLE_SIZE = 23;
    private static final int ID_ERROR_TOAST = 24;
    private static final int ID_SHOW_RECENTS = 25;
    private static final int ID_RECENTS_DELETED = 26;
    private static final int ID_RECENTS_WATCHED = 27;
    private static final int ID_RELATED_DELETED = 28;
    private static final int ID_RELATED_WATCHED = 29;
    private static final int ID_RECENTS_DAYS = 30;
    private static final int ID_LETTERBOX_BLACK = 31;
    private static final int ID_RECENTS_TRIM = 32;
    private static final int ID_TWEAKS = 33;
    private static final int ID_TWEAK_SEARCH_PKTS = 34;
    private static final int ID_LIVETV_ROWSIZE = 35;
    private static final int ID_VIDEO_PARENTAL = 36;
    private static final int ID_AUDIO_PAUSE = 37;
    private static final int ID_AUTOPLAY = 38;
    private static final int ID_AUDIO_SYNC = 39;
    private static final int ID_CAPTIONS = 40;
    private static final int ID_MERGE_VIDEOS = 41;
    private static final int ID_COMMSKIP = 43;
    private static final int ID_COMMSKIP_OFF = 44;
    private static final int ID_COMMSKIP_NOTIFY = 45;
    private static final int ID_COMMSKIP_SKIP = 46;
    private static final int ID_COMMSKIP_START = 47;
    private static final int ID_COMMSKIP_END = 48;
    private static final int ID_UPDOWN = 49;
    private static final int ID_UPDOWN_CONTROLS = 50;
    private static final int ID_UPDOWN_JUMP = 51;
    private static final int ID_UPDOWN_SKIPCOM = 52;
    private static final int ID_LEFTRIGHT = 53;
    private static final int ID_LEFTRIGHT_REWFF = 54;
    private static final int ID_LEFTRIGHT_JUMP = 55;
    private static final int ID_LEFTRIGHT_SKIPCOM = 56;
    private static final int ID_REWFF = 57;
    private static final int ID_REWFF_REWFF = 58;
    private static final int ID_REWFF_JUMP = 59;
    private static final int ID_REWFF_SKIPCOM = 60;
    private static final int ID_SPEED = 61;

    private static final String KEY_EXPAND = "EXPAND";

    private GuidedAction mBackendAction;
    private GuidedAction mAudioAction;
    private GuidedAction mCommskipAction;

    private String mPriorBackend;
    private String mPriorHttpPort;
    private String mPriorRowsize;
    private String mPriorParental;
    private ArrayList<String> mPlayGroupList;

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        Activity activity = getActivity();
        String title = getString(R.string.personal_settings);
        String breadcrumb = "";
        String description = getString(R.string.pref_title_settings);
        Drawable icon = activity.getDrawable(R.drawable.ic_settings);
        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("playgroups",mPlayGroupList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mPlayGroupList = savedInstanceState.getStringArrayList("playgroups");
        else
            mPlayGroupList = ( (SettingsActivity)getActivity() ).getPlayGroupList();  // ACTION_GETPLAYGROUPLIST
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {

        List<GuidedAction> subActions = new ArrayList<>();
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_BACKEND_IP)
                .title(R.string.pref_title_backend_ip)
                .description(Settings.getString("pref_backend"))
                .descriptionEditable(true)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_HTTP_PORT)
                .title(R.string.pref_title_http_port)
                .description(Settings.getString("pref_http_port"))
                .descriptionEditable(true)
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_BACKEND_MAC)
                .title(R.string.pref_title_backend_mac)
                .description(Settings.getString("pref_backend_mac"))
                .descriptionEditable(true)
                .build());
        actions.add(mBackendAction = new GuidedAction.Builder(getActivity())
                .id(ID_BACKEND)
                .title(R.string.pref_title_backend)
                .description(backendDesc())
                .subActions(subActions)
                .build());

        String str;
        // playback entries one per playback group
        for (int ix = 0 ; ix < mPlayGroupList.size(); ix++) {
            int addon = 100 * ix;
            String group = mPlayGroupList.get(ix);
            subActions = new ArrayList<>();
            str = Settings.getString("pref_framerate_match", group);
            GuidedAction.Builder tmp = new GuidedAction.Builder(getActivity());
            tmp.id(ID_FRAMERATE_MATCH + addon)
                    .title(R.string.pref_title_framerate_match)
                    .checked("true".equals(str))
                    .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID);
            if (android.os.Build.VERSION.SDK_INT < 23)
                tmp.description(R.string.pref_msg_needs_6_0)
                        .enabled(false);
            subActions.add(tmp.build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_SKIP_FWD + addon)
                    .title(R.string.pref_title_skip_fwd)
                    .description(Settings.getString("pref_skip_fwd", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_SKIP_BACK + addon)
                    .title(R.string.pref_title_skip_back)
                    .description(Settings.getString("pref_skip_back", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_JUMP + addon)
                    .title(R.string.pref_title_jump)
                    .description(Settings.getString("pref_jump", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());

            str = Settings.getString("pref_updown", group);
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_UPDOWN_CONTROLS + addon)
                    .title(R.string.pref_updown_controls)
                    .checked("controls".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_UPDOWN + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_UPDOWN_JUMP + addon)
                    .title(R.string.pref_updown_jump)
                    .checked("jump".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_UPDOWN + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_UPDOWN_SKIPCOM + addon)
                    .title(R.string.pref_updown_skipcom)
                    .checked("skipcom".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_UPDOWN + addon)
                    .build());

            str = Settings.getString("pref_leftright", group);
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_LEFTRIGHT_REWFF + addon)
                    .title(R.string.pref_leftright_rewff)
                    .checked("rewff".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_LEFTRIGHT + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_LEFTRIGHT_JUMP + addon)
                    .title(R.string.pref_leftright_jump)
                    .checked("jump".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_LEFTRIGHT + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_LEFTRIGHT_SKIPCOM + addon)
                    .title(R.string.pref_leftright_skipcom)
                    .checked("skipcom".equals(str))
                    .description(R.string.pref_arrows)
                    .checkSetId(ID_LEFTRIGHT + addon)
                    .build());

            str = Settings.getString("pref_rewff", group);
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_REWFF_REWFF + addon)
                    .title(R.string.pref_rewff_rewff)
                    .checked("rewff".equals(str))
                    .description(R.string.pref_rewff)
                    .checkSetId(ID_REWFF + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_REWFF_JUMP + addon)
                    .title(R.string.pref_rewff_jump)
                    .checked("jump".equals(str))
                    .description(R.string.pref_rewff)
                    .checkSetId(ID_REWFF + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_REWFF_SKIPCOM + addon)
                    .title(R.string.pref_rewff_skipcom)
                    .checked("skipcom".equals(str))
                    .description(R.string.pref_rewff)
                    .checkSetId(ID_REWFF + addon)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_SUBTITLE_SIZE + addon)
                    .title(R.string.pref_title_subtitle_size)
                    .description(Settings.getString("pref_subtitle_size", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());

            int i = Settings.getInt("pref_letterbox_color", group);
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_LETTERBOX_BLACK + addon)
                    .title(R.string.pref_letterbox_black)
                    .checked(i == Color.BLACK)
                    .description(R.string.pref_letterbox_black_desc)
                    .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                    .build());
            str = Settings.getString("pref_autoplay", group);
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_AUTOPLAY + addon)
                    .title(R.string.pref_autoplay)
                    .checked("true".equals(str))
                    .description(R.string.pref_autoplay_desc)
                    .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_CAPTIONS + addon)
                    .title(R.string.pref_captions)
                    .description(Settings.getString("pref_captions", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_AUDIO_SYNC + addon)
                    .title(R.string.pref_audio_sync)
                    .description(Settings.getString("pref_audio_sync", group))
                    .descriptionEditable(true)
                    // Fire Stick ignores the SIGNED part and allows only positive numbers.
//                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER
//                            | InputType.TYPE_NUMBER_FLAG_SIGNED)
                    .build());
            subActions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_SPEED + addon)
                    .title(R.string.pref_speed)
                    .description(Settings.getString("pref_speed", group))
                    .descriptionEditable(true)
                    .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                    .build());
            str = getContext().getString(R.string.pref_title_playback,group);
            actions.add(new GuidedAction.Builder(getActivity())
                    .id(ID_PLAYBACK + addon)
                    .title(str)
                    .subActions(subActions)
                    .build());
        }

        // Commercial Skip
        subActions = new ArrayList<>();
        // 0=off, 1=notify, 2=skip
        int commskip = Settings.getInt("pref_commskip");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP_OFF)
                .title(R.string.menu_commskip_off)
                .checked(commskip == 0)
                .description(R.string.pref_commskip_off_desc)
                .checkSetId(ID_COMMSKIP)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP_NOTIFY)
                .title(R.string.menu_commskip_notify)
                .checked(commskip == 1)
                .description(R.string.pref_commskip_notify_desc)
                .checkSetId(ID_COMMSKIP)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP_SKIP)
                .title(R.string.menu_commskip_skip)
                .checked(commskip == 2)
                .description(R.string.pref_commskip_skip_desc)
                .checkSetId(ID_COMMSKIP)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .title(R.string.pref_commskip_adj_title)
                .description(R.string.pref_commskip_adj_desc)
                .infoOnly(true)
                .multilineDescription(true)
                .build());
        // This should be signed number but fire stick does not support that
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP_START)
                .title(R.string.pref_commskip_start)
                .description(Settings.getString("pref_commskip_start"))
                .descriptionEditable(true)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP_END)
                .title(R.string.pref_commskip_end)
                .description(Settings.getString("pref_commskip_end"))
                .descriptionEditable(true)
                .build());
        actions.add(mCommskipAction = new GuidedAction.Builder(getActivity())
                .id(ID_COMMSKIP)
                .title(R.string.title_commskip)
                .description(commskipdesc())
                .subActions(subActions)
                .build());

        // Program List Options
        subActions = new ArrayList<>();
        str = Settings.getString("pref_seq");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_SORT_ORIG_AIRDATE)
                .title(R.string.pref_seq_orig_airdate)
                .checked("airdate".equals(str))
                .description(R.string.pref_seq_orig_airdate_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_seq_ascdesc");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_DESCENDING)
                .title(R.string.pref_seq_descending)
                .checked("desc".equals(str))
                .description(R.string.pref_seq_descending_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_merge_videos");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_MERGE_VIDEOS)
                .title(R.string.pref_merge_videos)
                .checked("true".equals(str))
                .description(R.string.pref_merge_videos_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        String recents = Settings.getString("pref_show_recents");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_SHOW_RECENTS)
                .title(R.string.pref_show_recents)
                .checked("true".equals(recents))
                .description(R.string.pref_show_recents_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RECENTS_DAYS)
                .title(R.string.pref_recents_days)
                .description(Settings.getString("pref_recents_days"))
                .descriptionEditable("true".equals(recents))
                .enabled("true".equals(recents))
                .focusable("true".equals(recents))
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());
        String recentsDel = Settings.getString("pref_recents_deleted");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RECENTS_DELETED)
                .title(R.string.pref_recents_deleted)
                .description(R.string.pref_recents_deleted_desc)
                .checked("true".equals(recentsDel))
                .enabled("true".equals(recents))
                .focusable("true".equals(recents))
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        String recentsWatched = Settings.getString("pref_recents_watched");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RECENTS_WATCHED)
                .title(R.string.pref_recents_watched)
                .description(R.string.pref_recents_watched_desc)
                .checked("true".equals(recentsWatched))
                .enabled("true".equals(recents))
                .focusable("true".equals(recents))
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_recents_trim");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RECENTS_TRIM)
                .title(R.string.pref_recents_trim)
                .description(R.string.pref_recents_trim_desc)
                .checked("true".equals(str))
                .enabled("true".equals(recents)
                        && ("true".equals(recentsDel) || "true".equals(recentsWatched)))
                .focusable("true".equals(recents)
                        && ("true".equals(recentsDel) || "true".equals(recentsWatched)))
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_related_deleted");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RELATED_DELETED)
                .title(R.string.pref_related_deleted)
                .checked("true".equals(str))
                .description(R.string.pref_related_deleted_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_related_watched");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_RELATED_WATCHED)
                .title(R.string.pref_related_watched)
                .checked("true".equals(str))
                .description(R.string.pref_related_watched_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_LIVETV_ROWSIZE)
                .title(R.string.pref_livetv_rowsize)
                .description(Settings.getString("pref_livetv_rowsize"))
                .descriptionEditable(true)
                .enabled(true)
                .focusable(true)
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_VIDEO_PARENTAL)
                .title(R.string.pref_video_parental)
                .description(Settings.getString("pref_video_parental"))
                .descriptionEditable(true)
                .enabled(true)
                .focusable(true)
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());

        actions.add(new GuidedAction.Builder(getActivity())
                .id(ID_PROG_LIST_OPTIONS)
                .title(R.string.pref_title_lists)
                .subActions(subActions)
                .build());

        subActions = new ArrayList<>();
        String audio = Settings.getString("pref_audio");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_AUDIO_AUTO)
                .title(R.string.pref_audio_auto)
                .checked("auto".equals(audio))
                .description(R.string.pref_audio_auto_desc)
                .checkSetId(ID_AUDIO)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_AUDIO_MEDIACODEC)
                .title(R.string.pref_audio_mediacodec)
                .checked("mediacodec".equals(audio))
                .description(R.string.pref_audio_mediacodec_desc)
                .checkSetId(ID_AUDIO)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_AUDIO_FFMPEG)
                .title(R.string.pref_audio_ffmpeg)
                .checked("ffmpeg".equals(audio))
                .description(R.string.pref_audio_ffmpeg_desc)
                .checkSetId(ID_AUDIO)
                .build());
        actions.add(mAudioAction = new GuidedAction.Builder(getActivity())
                .id(ID_AUDIO)
                .title(R.string.pref_ff_title)
                .description(audiodesc())
                .subActions(subActions)
                .build());

        subActions = new ArrayList<>();

        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_TWEAK_SEARCH_PKTS)
                .title(R.string.pref_tweak_ts_search_pkts)
                .description(Settings.getString("pref_tweak_ts_search_pkts"))
                .descriptionEditable(true)
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_LIVETV_DURATION)
                .title(R.string.pref_title_livetv_duration)
                .description(Settings.getString("pref_livetv_duration"))
                .descriptionEditable(true)
                .descriptionEditInputType(InputType.TYPE_CLASS_NUMBER)
                .build());
        // This is actually last play pos but stored as pref_bookmark
        // for backward compatibility
        str = Settings.getString("pref_bookmark");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_BOOKMARK_LOCAL)
                .title(R.string.pref_bookmark_local)
                .checked("local".equals(str))
                .description(R.string.pref_bookmark_mythtv)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_error_toast");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_ERROR_TOAST)
                .title(R.string.pref_error_toast)
                .checked("true".equals(str))
                .description(R.string.pref_error_toast_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        str = Settings.getString("pref_audio_pause");
        subActions.add(new GuidedAction.Builder(getActivity())
                .id(ID_AUDIO_PAUSE)
                .title(R.string.pref_audio_pause)
                .checked("true".equals(str))
                .description(R.string.pref_audio_pause_desc)
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .build());
        actions.add(new GuidedAction.Builder(getActivity())
                .id(ID_TWEAKS)
                .title(R.string.pref_tweaks_title)
                .subActions(subActions)
                .build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret =  super.onCreateView(inflater, container, savedInstanceState);
        int expandId = getActivity().getIntent()
                .getIntExtra(KEY_EXPAND, 0);
        GuidedAction action = findActionById(expandId);
        if (action != null)
            expandAction(action, false);
        return ret;
    }

    private String backendDesc() {
        return Settings.getString("pref_backend");
    }

    private String audiodesc() {
        return Settings.getString("pref_audio");
    }

    private String commskipdesc() {
        int ix = Settings.getInt("pref_commskip");
        Resources res = getResources();
        String[] values = res.getStringArray(R.array.menu_commskip);
        if (ix >= 0 && ix < values.length)
            return values[ix];
        else return "";
    }


    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        super.onGuidedActionClicked(action);
    }

    @Override
    public long onGuidedActionEditedAndProceed(GuidedAction action) {
        SharedPreferences.Editor editor = Settings.getEditor();
        int id = (int) action.getId();
        int actualId = id % 100;
        int groupId = id / 100;
        String group = mPlayGroupList.get(groupId);
        switch(actualId) {
            case ID_BACKEND_IP:
                String newVal = action.getDescription().toString();
                Settings.putString(editor, "pref_backend",newVal);
                mBackendAction.setDescription(newVal);
                notifyActionChanged(findActionPositionById(ID_BACKEND));
                break;
            case ID_HTTP_PORT:
                Settings.putString(editor, "pref_http_port",
                    validateNumber(action, 1, 65535, 6544));
                break;
            case ID_BACKEND_MAC:
                Settings.putString(editor, "pref_backend_mac",action.getDescription().toString());
                break;
            case ID_SKIP_FWD:
                Settings.putString(editor, "pref_skip_fwd",group,
                        action.getDescription().toString());
                break;
            case ID_SKIP_BACK:
                Settings.putString(editor, "pref_skip_back",group,
                        action.getDescription().toString());
                break;
            case ID_JUMP:
                Settings.putString(editor, "pref_jump",group,
                        action.getDescription().toString());
                break;
            case ID_LIVETV_DURATION:
                Settings.putString(editor, "pref_livetv_duration",
                        validateNumber(action, 30, 240, 60));
                break;
            case ID_SUBTITLE_SIZE:
                Settings.putString(editor, "pref_subtitle_size",group,
                        validateNumber(action, 25, 300, 100));
                break;
            case ID_RECENTS_DAYS:
                Settings.putString(editor, "pref_recents_days",
                        validateNumber(action, 1, 60, 7));
                break;
            case ID_TWEAK_SEARCH_PKTS:
                Settings.putString(editor, "pref_tweak_ts_search_pkts",
                        validateNumber(action, 600, 100000, 2600));
                break;
            case ID_LIVETV_ROWSIZE:
                Settings.putString(editor, "pref_livetv_rowsize",
                        validateNumber(action, 1, 100, 100));
                break;
            case ID_VIDEO_PARENTAL:
                Settings.putString(editor, "pref_video_parental",
                        validateNumber(action, 1, 4, 4));
                break;
            case ID_AUDIO_SYNC:
                Settings.putString(editor, "pref_audio_sync",group,
                        validateNumber(action, -2500, 2500, 0));
                break;
            case ID_COMMSKIP_START:
                Settings.putString(editor, "pref_commskip_start",
                        validateNumber(action, -10, 10, 0));
                break;
            case ID_COMMSKIP_END:
                Settings.putString(editor, "pref_commskip_end",
                        validateNumber(action, -10, 10, 0));
                break;
            case ID_CAPTIONS:
                Settings.putString(editor, "pref_captions",group,
                        validateNumber(action, 0, 10, 0));
                break;
            case ID_SPEED:
                Settings.putString(editor, "pref_speed",group,
                        validateNumber(action, 10, 800, 100));
                break;
            default:
                return GuidedAction.ACTION_ID_CURRENT;
        }
        editor.commit();
        return GuidedAction.ACTION_ID_CURRENT;
    }

    private static String validateNumber(GuidedAction action, int min, int max, int defValue) {
        String s;
        int i;
        s = action.getDescription().toString();
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            i = defValue;
        }
        if (i < min)
            i = min;
        else if (i > max)
            i = max;
        s = String.valueOf(i);
        action.setDescription(s);
        return s;
    }

    @Override
    public void onGuidedActionEditCanceled(GuidedAction action) {
        int id = (int) action.getId();
        int actualId = id % 100;
        int groupId = id / 100;
        String group = mPlayGroupList.get(groupId);
        switch(actualId) {
            case ID_BACKEND_IP:
                action.setDescription(Settings.getString("pref_backend"));
                break;
            case ID_BACKEND_MAC:
                action.setDescription(Settings.getString("pref_backend_mac"));
                break;
            case ID_HTTP_PORT:
                action.setDescription(Settings.getString("pref_http_port"));
                break;
            case ID_SKIP_FWD:
                action.setDescription(Settings.getString("pref_skip_fwd",group));
                break;
            case ID_SKIP_BACK:
                action.setDescription(Settings.getString("pref_skip_back",group));
                break;
            case ID_JUMP:
                action.setDescription(Settings.getString("pref_jump",group));
                break;
            case ID_LIVETV_DURATION:
                action.setDescription(Settings.getString("pref_livetv_duration"));
                break;
            case ID_SUBTITLE_SIZE:
                action.setDescription(Settings.getString("pref_subtitle_size",group));
                break;
            case ID_RECENTS_DAYS:
                action.setDescription(Settings.getString("pref_recents_days"));
                break;
            case ID_TWEAK_SEARCH_PKTS:
                action.setDescription(Settings.getString("pref_tweak_ts_search_pkts"));
                break;
            case ID_LIVETV_ROWSIZE:
                action.setDescription(Settings.getString("pref_livetv_rowsize"));
                break;
            case ID_VIDEO_PARENTAL:
                action.setDescription(Settings.getString("pref_video_parental"));
                break;
            case ID_AUDIO_SYNC:
                action.setDescription(Settings.getString("pref_audio_sync"));
                break;
            case ID_COMMSKIP_START:
                action.setDescription(Settings.getString("pref_commskip_start"));
                break;
            case ID_COMMSKIP_END:
                action.setDescription(Settings.getString("pref_commskip_end"));
                break;
            case ID_CAPTIONS:
                action.setDescription(Settings.getString("pref_captions"));
                break;
            case ID_SPEED:
                action.setDescription(Settings.getString("pref_speed"));
                break;
        }
    }

    @Override
    public boolean onSubGuidedActionClicked(GuidedAction action) {
        SharedPreferences.Editor editor = Settings.getEditor();
        int id = (int) action.getId();
        int actualId = id % 100;
        int groupId = id / 100;
        String group = mPlayGroupList.get(groupId);
        switch(actualId) {
            case ID_BOOKMARK_LOCAL:
                if (action.isChecked())
                    Settings.putString(editor, "pref_bookmark", "local");
                else
                    Settings.putString(editor, "pref_bookmark", "mythtv");
                break;
            case ID_FRAMERATE_MATCH:
                if (action.isChecked())
                    Settings.putString(editor, "pref_framerate_match",group,"true");
                else
                    Settings.putString(editor, "pref_framerate_match",group,"false");
                break;
            case ID_UPDOWN_CONTROLS:
                if (action.isChecked())
                    Settings.putString(editor, "pref_updown", group, "controls");
                break;
            case ID_UPDOWN_JUMP:
                if (action.isChecked())
                    Settings.putString(editor, "pref_updown", group, "jump");
                break;
            case ID_UPDOWN_SKIPCOM:
                if (action.isChecked())
                    Settings.putString(editor, "pref_updown", group, "skipcom");
                break;
            case ID_LEFTRIGHT_REWFF:
                if (action.isChecked())
                    Settings.putString(editor, "pref_leftright", group, "rewff");
                break;
            case ID_LEFTRIGHT_JUMP:
                if (action.isChecked())
                    Settings.putString(editor, "pref_leftright", group, "jump");
                break;
            case ID_LEFTRIGHT_SKIPCOM:
                if (action.isChecked())
                    Settings.putString(editor, "pref_leftright", group,"skipcom");
                break;
            case ID_REWFF_REWFF:
                if (action.isChecked())
                    Settings.putString(editor, "pref_rewff", group,"rewff");
                break;
            case ID_REWFF_JUMP:
                if (action.isChecked())
                    Settings.putString(editor, "pref_rewff", group,"jump");
                break;
            case ID_REWFF_SKIPCOM:
                if (action.isChecked())
                    Settings.putString(editor, "pref_rewff", group,"skipcom");
                break;
            case ID_SORT_ORIG_AIRDATE:
                if (action.isChecked())
                    Settings.putString(editor, "pref_seq", "airdate");
                else
                    Settings.putString(editor, "pref_seq", "rectime");
                break;
            case ID_DESCENDING:
                if (action.isChecked())
                    Settings.putString(editor, "pref_seq_ascdesc", "desc");
                else
                    Settings.putString(editor, "pref_seq_ascdesc", "asc");
                break;
            case ID_MERGE_VIDEOS:
                if (action.isChecked())
                    Settings.putString(editor, "pref_merge_videos", "true");
                else
                    Settings.putString(editor, "pref_merge_videos", "false");
                break;
            case ID_SHOW_RECENTS:
                if (action.isChecked())
                    Settings.putString(editor, "pref_show_recents", "true");
                else
                    Settings.putString(editor, "pref_show_recents", "false");
                restart(ID_PROG_LIST_OPTIONS);
                break;
            case ID_RECENTS_DELETED:
                if (action.isChecked())
                    Settings.putString(editor, "pref_recents_deleted", "true");
                else
                    Settings.putString(editor, "pref_recents_deleted", "false");
                if (! "true".equals(Settings.getString("pref_recents_watched")))
                    restart(ID_PROG_LIST_OPTIONS);
                break;
            case ID_RECENTS_WATCHED:
                if (action.isChecked())
                    Settings.putString(editor, "pref_recents_watched", "true");
                else
                    Settings.putString(editor, "pref_recents_watched", "false");
                if (! "true".equals(Settings.getString("pref_recents_deleted")))
                    restart(ID_PROG_LIST_OPTIONS);
                break;
            case ID_RECENTS_TRIM:
                if (action.isChecked())
                    Settings.putString(editor, "pref_recents_trim", "true");
                else
                    Settings.putString(editor, "pref_recents_trim", "false");
                break;
            case ID_RELATED_DELETED:
                if (action.isChecked())
                    Settings.putString(editor, "pref_related_deleted", "true");
                else
                    Settings.putString(editor, "pref_related_deleted", "false");
                break;
            case ID_RELATED_WATCHED:
                if (action.isChecked())
                    Settings.putString(editor, "pref_related_watched", "true");
                else
                    Settings.putString(editor, "pref_related_watched", "false");
                break;
            case ID_AUDIO_AUTO:
                if (action.isChecked())
                    Settings.putString(editor, "pref_audio", "auto");
                break;
            case ID_AUDIO_MEDIACODEC:
                if (action.isChecked())
                    Settings.putString(editor, "pref_audio", "mediacodec");
                break;
            case ID_AUDIO_FFMPEG:
                if (action.isChecked())
                    Settings.putString(editor, "pref_audio", "ffmpeg");
                break;
            case ID_ERROR_TOAST:
                if (action.isChecked())
                    Settings.putString(editor, "pref_error_toast", "true");
                else
                    Settings.putString(editor, "pref_error_toast", "false");
                break;
            case ID_AUDIO_PAUSE:
                if (action.isChecked())
                    Settings.putString(editor, "pref_audio_pause", "true");
                else
                    Settings.putString(editor, "pref_audio_pause", "false");
                break;
            case ID_LETTERBOX_BLACK:
                if (action.isChecked())
                    Settings.putString(editor, "pref_letterbox_color",group,
                            String.valueOf(Color.BLACK));
                else
                    Settings.putString(editor, "pref_letterbox_color",group,
                            String.valueOf(Color.DKGRAY));
                break;
            case ID_AUTOPLAY:
                if (action.isChecked())
                    Settings.putString(editor, "pref_autoplay", group, "true");
                else
                    Settings.putString(editor, "pref_autoplay", group, "false");
                break;
            case ID_COMMSKIP_OFF:
                if (action.isChecked())
                    Settings.putString(editor, "pref_commskip", "0");
                break;
            case ID_COMMSKIP_NOTIFY:
                if (action.isChecked())
                    Settings.putString(editor, "pref_commskip", "1");
                break;
            case ID_COMMSKIP_SKIP:
                if (action.isChecked())
                    Settings.putString(editor, "pref_commskip", "2");
                break;
            default:
                return false;
        }
        editor.commit();
        notifyActionChanged(findActionPositionById(ID_PLAYBACK));
        mAudioAction.setDescription(audiodesc());
        notifyActionChanged(findActionPositionById(ID_AUDIO));
        mCommskipAction.setDescription(commskipdesc());
        notifyActionChanged(findActionPositionById(ID_COMMSKIP));
        return false;
    }

    private void restart(int key) {
        // restart the activity so the recent options below get appropriately
        // enabled or disabled
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        intent.putExtra(KEY_EXPAND, key);
        getContext().startActivity(intent);
        finishGuidedStepSupportFragments();
    }

    @Override
    public void onResume() {
        mPriorBackend = Settings.getString("pref_backend");
        mPriorHttpPort =  Settings.getString("pref_http_port");
        mPriorRowsize =  Settings.getString("pref_livetv_rowsize");
        mPriorParental =  Settings.getString("pref_video_parental");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!Objects.equals(mPriorBackend, Settings.getString("pref_backend"))
            || !Objects.equals(mPriorHttpPort, Settings.getString("pref_http_port"))
            || !Objects.equals(mPriorRowsize, Settings.getString("pref_livetv_rowsize"))
            || !Objects.equals(mPriorParental, Settings.getString("pref_video_parental")))
            MainFragment.startFetch(-1, null, null, true);
        mPriorBackend = null;
        mPriorHttpPort = null;
        mPriorRowsize = null;
        mPriorParental = null;
    }
}
