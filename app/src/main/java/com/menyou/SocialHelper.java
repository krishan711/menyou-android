/*
 * Created by Krishan Patel.
 * Copyright (c) 2014. Rocko Labs Ltd. All Rights Reserved.
 */

package com.menyou;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.List;

public class SocialHelper
{
    public interface LoginListener
    {
        public void onFailed();
        public void onDetailRequestStarted();
        public void onSuccess(String id, String formatted, Object o, Object o1);
    }

    public interface ProfilePictureListener
    {
        public void onProfilePictureRetreived(Bitmap profilePic);
    }

    public interface GetFriendsListener
    {
        public void onFriendsLoaded(List<String> friendIds);
    }

    public interface ShareListener
    {
        public void onCompleted(boolean success);
    }

    private static final int ACTIVITY_GOOGLE_CONNECTION = 999;

    private Activity activity;

    private GoogleApiClient googleClient;
    private ConnectionResult googleConnectionResult;
    private LoginListener googleLoginListener;

    public void onStart()
    {
        if (googleClient != null)
            googleClient.connect();
    }

    public void onStop()
    {
        if (googleClient != null && googleClient.isConnected())
            googleClient.disconnect();
    }

//    public void onResume()
//    {
//        if (facebookLifecycleHelper != null)
//            facebookLifecycleHelper.onResume();
//    }
//
//    public void onPause()
//    {
//        if (facebookLifecycleHelper != null)
//            facebookLifecycleHelper.onPause();
//    }
//
//    public void onDestroy()
//    {
//        if (facebookLifecycleHelper != null)
//            facebookLifecycleHelper.onDestroy();
//    }
//
//    public void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState)
//    {
//        if (facebookLifecycleHelper != null)
//            facebookLifecycleHelper.onSaveInstanceState(outState);
//    }
//
//    public boolean isFacebookLoggedIn()
//    {
//        Session session = Session.getActiveSession();
//        return session != null && session.isOpened() && session.getAccessToken() != null;
//    }
//
//    public boolean isTwitterLoggedIn()
//    {
//        try
//        {
//            return twitterClient != null && twitterClient.getOAuthAccessToken() != null;
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }

    public boolean isGoogleLoggedIn()
    {
        return googleClient != null && googleClient.isConnected();
    }

    public void initSocial(Activity activity, Bundle savedInstanceState)
    {
        if (this.activity == activity)
            return;

        this.activity = activity;
//        initFacebook(savedInstanceState);
//        initTwitter();
        initGoogle();
    }

//    public boolean isLoggedIn(SocialType socialType)
//    {
//        switch (socialType)
//        {
//            case FACEBOOK:
//                return isFacebookLoggedIn();
//            case TWITTER:
//                return isTwitterLoggedIn();
//            case GOOGLE:
//                return isGoogleLoggedIn();
//        }
//        return false;
//    }

//    protected void initFacebook(Bundle savedInstanceState)
//    {
//        facebookCallback = new Session.StatusCallback()
//        {
//            @Override
//            public void call(Session session, SessionState sessionState, Exception e)
//            {
//                Log.i("Menyou", "facebook session: " + session);
//                if (facebookLoginListener == null)
//                {
//                    Log.i("Menyou", "no facebookLoginListener");
//                    return;
//                }
//
//                if (session.isOpened())
//                {
//                    facebookLoginListener.onDetailRequestStarted();
//                    Request.newMeRequest(session, new Request.GraphUserCallback()
//                    {
//                        @Override
//                        public void onCompleted(GraphUser user, Response response)
//                        {
//                            Log.i("Menyou", "Facebook logged in: " + user);
//                            if (facebookLoginListener == null)
//                                return;
//
//                            if (user == null)
//                                facebookLoginListener.onFailed();
//                            else
//                                facebookLoginListener.onSuccess(SocialType.FACEBOOK, user.getId(), user.getName(), user.asMap().get("email").toString(), user.getUsername());
//                            facebookLoginListener = null;
//                        }
//                    }).executeAsync();
//                }
//                else if (session.isClosed())
//                {
//                    facebookLoginListener.onFailed();
//                    facebookLoginListener = null;
//                }
//            }
//        };
//        facebookLifecycleHelper = new UiLifecycleHelper(activity, facebookCallback);
//        facebookLifecycleHelper.onCreate(savedInstanceState);
//        Session.openActiveSession(activity, false, facebookCallback);
//    }
//
//    protected void initTwitter()
//    {
//        ConfigurationBuilder builder = new ConfigurationBuilder();
//        builder.setOAuthConsumerKey("tuucJW1VZm9tQdVhRHMnKjNxn");
//        builder.setOAuthConsumerSecret("iZX9Gl33F6BroPha8EYM75oCegga3oXYFNr3CjMIHmHkSklYDJ");
//        Configuration configuration = builder.build();
//        twitterClient = new TwitterFactory(configuration).getInstance();
//        if (preferences.getString(PREFERENCE_TWITTER_AUTH_TOKEN, null) != null)
//        {
//            String token = preferences.getString(PREFERENCE_TWITTER_AUTH_TOKEN, null);
//            String secret = preferences.getString(PREFERENCE_TWITTER_AUTH_SECRET, null);
//            twitterClient.setOAuthAccessToken(new twitter4j.auth.AccessToken(token, secret));
//        }
//    }

    protected void initGoogle()
    {
        GoogleApiClient.Builder googleBuilder = new GoogleApiClient.Builder(activity);
        googleBuilder.addApi(Plus.API);
        googleBuilder.addScope(Plus.SCOPE_PLUS_LOGIN);
        googleBuilder.addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
        {
            @Override
            public void onConnected(Bundle bundle)
            {
                Log.i("Menyou", "google connected");
                if (googleLoginListener != null)
                {
                    Person user = Plus.PeopleApi.getCurrentPerson(googleClient);
                    Log.i("Menyou", "Google logged in: " + user);
                    googleLoginListener.onSuccess(user.getId(), user.getName().getFormatted(), null, null);
                    googleLoginListener = null;
                }
            }

            @Override
            public void onConnectionSuspended(int cause)
            {
                Log.i("Menyou", "google connection suspended: " + cause);
                if (googleLoginListener != null)
                {
                    googleLoginListener.onFailed();
                    googleLoginListener = null;
                }
            }
        });
        googleBuilder.addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener()
        {
            @Override
            public void onConnectionFailed(ConnectionResult result)
            {
                Log.i("Menyou", "google connection failed: " + result);
                googleConnectionResult = result;
                if (googleLoginListener != null)
                    startGoogleLogin(googleLoginListener);
            }
        });
        googleClient = googleBuilder.build();
    }

//    public void startLogin(SocialType socialType, LoginListener loginListener)
//    {
//        switch (socialType)
//        {
//            case FACEBOOK:
//                startFacebookLogin(loginListener);
//                break;
//            case TWITTER:
//                startTwitterLogin(loginListener);
//                break;
//            case GOOGLE:
//                startGoogleLogin(loginListener);
//                break;
//        }
//    }
//
//    public void startFacebookLogin(LoginListener facebookLoginListener)
//    {
//        this.facebookLoginListener = facebookLoginListener;
//        Session session = Session.getActiveSession();
//        if (session.isOpened())
//        {
//            facebookCallback.call(session, session.getState(), null);
//        }
////        else if (!session.isClosed())
////        {
////            session.openForRead(new Session.OpenRequest(activity).setPermissions("public_profile", "email").setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK));
////        }
//        else
//        {
//            Session.openActiveSession(activity, true, facebookCallback);
//        }
//    }
//
//    public void startTwitterLogin(LoginListener twitterLoginListener)
//    {
//        this.twitterLoginListener = twitterLoginListener;
//        if (preferences.getString(PREFERENCE_TWITTER_AUTH_TOKEN, null) != null)
//        {
//            String token = preferences.getString(PREFERENCE_TWITTER_AUTH_TOKEN, null);
//            String secret = preferences.getString(PREFERENCE_TWITTER_AUTH_SECRET, null);
//            twitterClient.setOAuthAccessToken(new twitter4j.auth.AccessToken(token, secret));
//            twitterLoggedIn();
//            return;
//        }
//
//        final TwitterLoginDialog loginDialog = TwitterLoginDialog.newInstance();
//        loginDialog.start(activity.getFragmentManager(), twitterClient, new TwitterLoginDialog.Listener()
//        {
//            @Override
//            public void onSuccess(twitter4j.auth.AccessToken accessToken)
//            {
//                loginDialog.dismiss();
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(PREFERENCE_TWITTER_AUTH_TOKEN, accessToken.getToken());
//                editor.putString(PREFERENCE_TWITTER_AUTH_SECRET, accessToken.getTokenSecret());
//                editor.apply();
//                twitterLoggedIn();
//            }
//
//            @Override
//            public void onFailure(TwitterLoginDialog.Result result)
//            {
//                loginDialog.dismiss();
//                twitterLoggedIn();
//            }
//        });
//    }
//
//    private void twitterLoggedIn()
//    {
//        if (twitterLoginListener != null)
//        {
//            try
//            {
//                if (twitterClient.getOAuthAccessToken().getToken() != null)
//                {
//                    twitterLoginListener.onDetailRequestStarted();
//                    new TwitterGetUserDetailsTask(twitterClient, new TwitterGetUserDetailsTask.Listener() {
//                        @Override
//                        public void onDetailsRetrieved(User user)
//                        {
//                            Log.i("Menyou", "Twitter logged in: " + user);
//                            twitterLoginListener.onSuccess(SocialType.TWITTER, String.valueOf(user.getId()), user.getName(), null, user.getScreenName());
//                            twitterLoginListener = null;
//                        }
//                    }).execute(String.valueOf(twitterClient.getOAuthAccessToken().getUserId()));
//                }
//                else
//                {
//                    twitterLoginListener.onFailed();
//                    twitterLoginListener = null;
//                }
//            }
//            catch (Exception e)
//            {
//                twitterLoginListener.onFailed();
//                twitterLoginListener = null;
//            }
//        }
//    }

    public void startGoogleLogin(LoginListener googleLoginListener)
    {
        this.googleLoginListener = googleLoginListener;
        if (googleConnectionResult != null && googleConnectionResult.hasResolution())
        {
            try
            {
                googleConnectionResult.startResolutionForResult(activity, ACTIVITY_GOOGLE_CONNECTION);
            }
            catch (IntentSender.SendIntentException e)
            {
                googleConnectionResult = null;
                googleClient.connect();
            }
        }
        else if (googleClient.isConnected())
        {
            googleConnectionResult = null;
            Person user = Plus.PeopleApi.getCurrentPerson(googleClient);
            Log.i("Menyou", "Google logged in: " + user);
            googleLoginListener.onSuccess(user.getId(), user.getName().getFormatted(), null, null);
            this.googleLoginListener = null;
        }
        else
        {
            googleConnectionResult = null;
            googleClient.connect();
        }
    }

//    public void getProfilePicture(SocialType socialType, String userId, int size, ProfilePictureListener listener)
//    {
//        switch (socialType)
//        {
//            case FACEBOOK:
//                getFacebookProfilePicture(userId, size, listener);
//                break;
//            case TWITTER:
//                getTwitterProfilePicture(userId, size, listener);
//                break;
//            case GOOGLE:
//                getGoogleProfilePicture(userId, size, listener);
//                break;
//        }
//    }
//
//    public void getFacebookProfilePicture(String userId, int size, final ProfilePictureListener listener)
//    {
//        Picasso.with(activity).load("https://graph.facebook.com/" + userId + "/picture?type=square&width=" + size + "&height=" + size).into(new Target()
//        {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom)
//            {
//                listener.onProfilePictureRetreived(bitmap);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable drawable)
//            {
//                listener.onProfilePictureRetreived(null);
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable drawable) {}
//        });
//    }
//
//    public void getTwitterProfilePicture(String userId, final int size, final ProfilePictureListener listener)
//    {
//        new TwitterGetUserDetailsTask(twitterClient, new TwitterGetUserDetailsTask.Listener() {
//            @Override
//            public void onDetailsRetrieved(User user)
//            {
//                if (user != null)
//                {
//                    Picasso.with(application).load(user.getOriginalProfileImageURL()).resize(size, size).into(new Target()
//                    {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom)
//                        {
//                            listener.onProfilePictureRetreived(bitmap);
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable drawable)
//                        {
//                            listener.onProfilePictureRetreived(null);
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable drawable) {}
//                    });
//                }
//                else
//                {
//                    listener.onProfilePictureRetreived(null);
//                }
//            }
//        }).execute(userId);
//    }

//    public void getGoogleProfilePicture(String userId, int size, final ProfilePictureListener listener)
//    {
//        try
//        {
//            HttpGet httpGet = new HttpGet("https://www.googleapis.com/plus/v1/people/" + userId + "?fields=image&key=AIzaSyBItx-NF4RW7GhpCT4YfGKXgqdsDr85g4g");
//            HttpResponse response = new DefaultHttpClient().execute(httpGet);
//            JSONObject json = new JSONObject(EntityUtils.toString(response.getEntity()));
//            String imageUrl = json.getJSONObject("image").getString("url");
//            Picasso.with(application).load(imageUrl + "&sz=" + size).into(new Target()
//            {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom)
//                {
//                    listener.onProfilePictureRetreived(bitmap);
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable drawable)
//                {
//                    listener.onProfilePictureRetreived(null);
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable drawable) {}
//            });
//        }
//        catch (Exception e)
//        {
//            listener.onProfilePictureRetreived(null);
//        }
//    }

//    public void getFriends(SocialType socialType, GetFriendsListener listener)
//    {
//        switch (socialType)
//        {
//            case FACEBOOK:
//                getFacebookFriends(listener);
//                break;
//            case TWITTER:
//                getTwitterFriends(listener);
//                break;
//            case GOOGLE:
//                getGoogleFriends(listener);
//                break;
//        }
//    }
//
//    public void getFacebookFriends(final GetFriendsListener listener)
//    {
//        Request.newMyFriendsRequest(Session.getActiveSession(), new Request.GraphUserListCallback() {
//            @Override
//            public void onCompleted(List<GraphUser> graphUsers, Response response)
//            {
//                if (graphUsers == null)
//                {
//                    listener.onFriendsLoaded(SocialType.FACEBOOK, null);
//                    return;
//                }
//
//                List<String> friendIds = new ArrayList<>();
//                for (GraphUser user : graphUsers)
//                    friendIds.add(user.getId());
//                listener.onFriendsLoaded(SocialType.FACEBOOK, friendIds);
//            }
//        }).executeAsync();
//    }
//
//    public void getTwitterFriends(final GetFriendsListener listener)
//    {
//        new TwitterGetFriendIdsTask(twitterClient, new TwitterGetFriendIdsTask.Listener() {
//            @Override
//            public void onFriendIdsRetrieved(List<String> friendIds)
//            {
//                listener.onFriendsLoaded(SocialType.TWITTER, friendIds);
//            }
//        }).execute();
//    }

//    public void getGoogleFriends(final GetFriendsListener listener)
//    {
//        new GoogleGetFriendsTask(googleClient, new GoogleGetFriendsTask.Listener() {
//            @Override
//            public void onFriendIdsRetrieved(List<String> friendIds)
//            {
//                listener.onFriendsLoaded(SocialType.GOOGLE, friendIds);
//            }
//        }).execute();
//    }

//    public boolean shareFacebook(String text, String caption, String url, Activity activity, final ShareListener listener)
//    {
//        if (FacebookDialog.canPresentShareDialog(activity, FacebookDialog.ShareDialogFeature.SHARE_DIALOG))
//        {
//            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(activity).setDescription(text).setLink(url).setCaption(caption).build();
//            shareDialog.present();
//            return true;
//        }
//        else
//        {
//            Bundle params = new Bundle();
//            params.putString("name", activity.getString(R.string.app_name));
//            params.putString("caption", caption);
//            params.putString("description", text);
//            params.putString("link", url);
//            WebDialog.FeedDialogBuilder feedDialogBuilder = (new WebDialog.FeedDialogBuilder(activity, Session.getActiveSession(), params));
//            feedDialogBuilder.setOnCompleteListener(new WebDialog.OnCompleteListener() {
//                @Override
//                public void onComplete(Bundle values, FacebookException error) {
//                    listener.onCompleted(error == null);
//                }
//            });
//            feedDialogBuilder.build().show();
//            return true;
//        }
//    }
//
//    public boolean shareTwitter(String text, String url, Activity activity, Fragment fragment)
//    {
//        Intent tweetIntent = new Intent();
//        tweetIntent.setData(Uri.parse("http://www.twitter.com/intent/tweet?text=" + text + "&url=" + url));
//        for (ResolveInfo resolveInfo : activity.getPackageManager().queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY))
//        {
//            if (resolveInfo.activityInfo.packageName != null)
//            {
//                tweetIntent.setPackage(resolveInfo.activityInfo.packageName);
//                if (fragment != null)
//                    fragment.startActivityForResult(tweetIntent, 0);
//                else
//                    activity.startActivityForResult(tweetIntent, 0);
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean shareGoogle(String text, String url, Activity activity, Fragment fragment)
//    {
//        Intent shareIntent = new PlusShare.Builder(activity).setType("text/plain").setText(text).setContentUrl(Uri.parse(url)).getIntent();
//        if (fragment != null)
//            fragment.startActivityForResult(shareIntent, 0);
//        else
//            activity.startActivityForResult(shareIntent, 0);
//        return true;
//    }

    public boolean onActivityResult(int requestCode, int responseCode, Intent data)
    {
        if (requestCode == ACTIVITY_GOOGLE_CONNECTION)
        {
            googleConnectionResult = null;
            if (!googleClient.isConnecting())
                googleClient.connect();
            return true;
        }

//        if (facebookLifecycleHelper != null)
//            facebookLifecycleHelper.onActivityResult(requestCode, responseCode, data);
        return false;
    }

}
