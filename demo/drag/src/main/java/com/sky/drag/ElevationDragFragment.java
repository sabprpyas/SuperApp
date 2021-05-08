/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.drag;



import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

public class ElevationDragFragment extends Fragment {



    /* The circular outline provider */
    private ViewOutlineProvider mOutlineProviderCircle;

    /* The current elevation of the floating view. */
    private float mElevation = 0;

    /* The step in elevation when changing the Z value */
    private int mElevationStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOutlineProviderCircle = new CircleOutlineProvider();

        mElevationStep = getResources().getDimensionPixelSize(R.dimen.elevation_step);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    		View rootView = inflater.inflate(R.layout.ztranslation,container, false);
    		final View floatingShape = rootView.findViewById(R.id.circle);
    		
    		floatingShape.setOutlineProvider(mOutlineProviderCircle);
    		floatingShape.setClipToOutline(true);
    		
    		DragFrameLayout dragLayout = (DragFrameLayout)rootView.findViewById(R.id.main_layout);
    		dragLayout.setDragFrameController(new DragFrameLayout.DragFrameLayoutController()
			{
				
				@Override
				public void onDragDrop(boolean captured)
				{
					//  通过动画的方式让视图的阴影增大和减小
					floatingShape.animate().translationZ(captured?50:0).setDuration(100);
					
				}
			});
    		
    		dragLayout.addDragView(floatingShape);
    		
    		rootView.findViewById(R.id.raise_bt).setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					mElevation += mElevationStep;
					floatingShape.setElevation(mElevation);
					
				}
			});
    		
    		rootView.findViewById(R.id.lower_bt).setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					mElevation -= mElevationStep;
					if(mElevation < 0)
					{
						mElevation = 0;
					}
					
					floatingShape.setElevation(mElevation);
					
				}
			});
    		return rootView;
    }

    /**
     * ViewOutlineProvider which sets the outline to be an oval which fits the view bounds.
     */
    private class CircleOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
           outline.setOval(0, 0, view.getWidth(), view.getHeight());
       
        }
    }

}