package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JokeTaskAndroidTest {
    private static final String IP_ADDRESS = "#IP_ADDRESS";
    private static final String DEFAULT_JOKE = "This is a funny joke!";

    @Mock
    private Context mMockContext;

    @Mock
    private Resources mMockResources;

    @Mock
    private View mMockView;

    @Before
    public void setup() {
        when(mMockContext.getResources()).thenReturn(mMockResources);
        when(mMockResources.getString(R.string.ip_address)).thenReturn(IP_ADDRESS);
    }

    @Test
    public void testJokeMakerTask() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        JokeMakerAsyncTask testTask = new JokeMakerAsyncTask(mMockContext, mMockView) {
            @Override
            protected void onPreExecute() {
                assertTrue(true);
            }

            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                assertTrue(result.length() > 0);
                assertEquals(result, DEFAULT_JOKE);
                signal.countDown();
            }
        };

        testTask.execute();
        signal.await();
    }
}
