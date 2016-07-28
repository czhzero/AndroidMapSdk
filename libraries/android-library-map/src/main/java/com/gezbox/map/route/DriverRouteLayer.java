package com.gezbox.map.route;

import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.gezbox.map.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhaohua on 16/7/27.
 *
 * 这个类有时间需要重构，需结合步行路线 
 *
 */
public class DriverRouteLayer {

    private AMap mAMap;
    private LatLng mStartPoint;
    private LatLng mEndPoint;
    private Marker mStartMarker;
    private Marker mEndMarker;

    private DrivePath drivePath;
    private List<LatLng> mLatLngsOfPath;                                //路径上的点
    private List<LatLonPoint> mThroughPointList;                        //路径上的点
    private List<Marker> mThroughPointMarkerList = new ArrayList<>();   //路径上的marker
    private List<TMC> mTmcs;                                            //路径拥堵情况

    private boolean throughPointMarkerVisible = true;

    private PolylineOptions mPolylineOptions;
    private boolean isColorfulline = true;
    private float mWidth = 15;
    protected boolean nodeIconVisible = true;

    protected List<Marker> stationMarkers = new ArrayList<>();
    protected List<Polyline> allPolyLines = new ArrayList<>();


    public void setIsColorfulline(boolean iscolorfulline) {
        this.isColorfulline = iscolorfulline;
    }

    /**
     * 根据给定的参数，构造一个导航路线图层类对象。
     * @param amap 地图对象。
     * @param path 导航路线规划方案。
     * @param start 起点
     * @param end 终点
     * @param throughPointList 途径点
     */
    public DriverRouteLayer(AMap amap, DrivePath path,
                            LatLonPoint start, LatLonPoint end, List<LatLonPoint> throughPointList) {
        mAMap = amap;
        drivePath = path;
        mStartPoint = new LatLng(start.getLatitude(),start.getLongitude());
        mEndPoint = new LatLng(end.getLatitude(),end.getLongitude());
        mThroughPointList = throughPointList;
    }

    /**
     * 设置路线宽度
     * @param width 路线宽度，取值范围：大于0
     */
    public void setRouteWidth(float width) {
        mWidth = width;
    }

    /**
     * 添加路线添加到地图上显示。
     */
    public void addToMap() {

        initPolylineOptions();

        try {

            if (mAMap == null) {
                return;
            }

            if (mWidth == 0 || drivePath == null) {
                return;
            }

            mLatLngsOfPath = new ArrayList<>();

            mTmcs = new ArrayList<>();

            List<DriveStep> drivePaths = drivePath.getSteps();

            mPolylineOptions.add(mStartPoint);

            for (DriveStep step : drivePaths) {
                List<LatLonPoint> latlonPoints = step.getPolyline();
                List<TMC> tmclist = step.getTMCs();
                mTmcs.addAll(tmclist);
                addDrivingStationMarkers(step, convertToLatLng(latlonPoints.get(0)));
                for (LatLonPoint latlonpoint : latlonPoints) {
                    mPolylineOptions.add(convertToLatLng(latlonpoint));
                    mLatLngsOfPath.add(convertToLatLng(latlonpoint));
                }
            }

            mPolylineOptions.add(mEndPoint);

            if (mStartMarker != null) {
                mStartMarker.remove();
                mStartMarker = null;
            }
            if (mEndMarker != null) {
                mEndMarker.remove();
                mEndMarker = null;
            }
            addStartAndEndMarker();
            addThroughPointMarker();
            if (isColorfulline && mTmcs.size()>0 ) {
                colorWayUpdate(mTmcs);
            }else {
                showPolyline();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void addStartAndEndMarker() {
        mStartMarker = mAMap.addMarker((new MarkerOptions())
                .position(mStartPoint).icon(getStartBitmapDescriptor())
                .title("\u8D77\u70B9"));
        mEndMarker = mAMap.addMarker((new MarkerOptions()).position(mEndPoint)
                .icon(getEndBitmapDescriptor()).title("\u7EC8\u70B9"));
    }

    /**
     * 初始化线段属性
     */
    private void initPolylineOptions() {

        mPolylineOptions = null;
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(getDriveColor()).width(mWidth);
    }



    private void showPolyline() {
        addPolyLine(mPolylineOptions);
    }




    private void colorWayUpdate(List<TMC> tmcSection) {
        if (mAMap == null) {
            return;
        }
        if (mLatLngsOfPath == null || mLatLngsOfPath.size() <= 0) {
            return;
        }
        if (tmcSection == null || tmcSection.size() <= 0) {
            return;
        }
        int j = 0;
        LatLng startLatLng = mLatLngsOfPath.get(0);
        LatLng endLatLng = null;
        double segmentTotalDistance = 0;
        TMC segmentTrafficStatus;
        List<LatLng> tempList = new ArrayList<LatLng>();
        //画出起点到规划路径之间的连线
        addPolyLine(new PolylineOptions().add(mStartPoint, startLatLng)
                .setDottedLine(true));
        //终点和规划路径之间连线
        addPolyLine(new PolylineOptions().add(mLatLngsOfPath.get(mLatLngsOfPath.size() - 1),
                mEndPoint).setDottedLine(true));
        for (int i = 0; i < mLatLngsOfPath.size() && j < tmcSection.size(); i++) {
            segmentTrafficStatus = tmcSection.get(j);
            endLatLng = mLatLngsOfPath.get(i);
            double distanceBetweenTwoPosition = AMapUtils.calculateLineDistance(startLatLng, endLatLng);
            segmentTotalDistance = segmentTotalDistance + distanceBetweenTwoPosition;
            if (segmentTotalDistance > segmentTrafficStatus.getDistance() + 1) {
                double toSegDis = distanceBetweenTwoPosition - (segmentTotalDistance - segmentTrafficStatus.getDistance());
                LatLng middleLatLng = getPointForDis(startLatLng, endLatLng, toSegDis);
                tempList.add(middleLatLng);
                startLatLng = middleLatLng;
                i--;
            } else {
                tempList.add(endLatLng);
                startLatLng = endLatLng;
            }
            if (segmentTotalDistance >= segmentTrafficStatus.getDistance() || i == mLatLngsOfPath.size() - 1) {
                if (j == tmcSection.size() - 1 && i < mLatLngsOfPath.size() - 1) {
                    for (i++; i < mLatLngsOfPath.size(); i++) {
                        LatLng lastLatLng = mLatLngsOfPath.get(i);
                        tempList.add(lastLatLng);
                    }
                }
                j++;
                if (segmentTrafficStatus.getStatus().equals("畅通")) {
                    addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.GREEN));
                } else if (segmentTrafficStatus.getStatus().equals("缓行")) {
                    addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.YELLOW));
                } else if (segmentTrafficStatus.getStatus().equals("拥堵")) {
                    addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.RED));
                } else if (segmentTrafficStatus.getStatus().equals("严重拥堵")) {
                    addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.parseColor("#990033")));
                } else {
                    addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.parseColor("#537edc")));
                }
                tempList.clear();
                tempList.add(startLatLng);
                segmentTotalDistance = 0;
            }
            if (i == mLatLngsOfPath.size() - 1) {
                addPolyLine(new PolylineOptions().add(endLatLng, mEndPoint)
                        .setDottedLine(true));
            }
        }
    }

    private LatLng convertToLatLng(LatLonPoint point) {
        return new LatLng(point.getLatitude(),point.getLongitude());
    }

    /**
     * @param driveStep
     * @param latLng
     */
    private void addDrivingStationMarkers(DriveStep driveStep, LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng)
                .title("\u65B9\u5411:" + driveStep.getAction()
                        + "\n\u9053\u8DEF:" + driveStep.getRoad())
                .snippet(driveStep.getInstruction()).visible(nodeIconVisible)
                .anchor(0.5f, 0.5f).icon(getDriveBitmapDescriptor());
        if(options == null) {
            return;
        }
        Marker marker = mAMap.addMarker(options);
        if(marker != null) {
            stationMarkers.add(marker);
        }
    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(new LatLng(mStartPoint.latitude, mStartPoint.longitude));
        b.include(new LatLng(mEndPoint.latitude, mEndPoint.longitude));
        if (this.mThroughPointList != null && this.mThroughPointList.size() > 0) {
            for (int i = 0; i < this.mThroughPointList.size(); i++) {
                b.include(new LatLng(
                        this.mThroughPointList.get(i).getLatitude(),
                        this.mThroughPointList.get(i).getLongitude()));
            }
        }
        return b.build();
    }


    private void addThroughPointMarker() {
        if (this.mThroughPointList != null && this.mThroughPointList.size() > 0) {
            LatLonPoint latLonPoint = null;
            for (int i = 0; i < this.mThroughPointList.size(); i++) {
                latLonPoint = this.mThroughPointList.get(i);
                if (latLonPoint != null) {
                    mThroughPointMarkerList.add(mAMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude()))
                            .visible(throughPointMarkerVisible)
                            .icon(getThroughPointBitDes())
                            .title("\u9014\u7ECF\u70B9")));
                }
            }
        }
    }

    private BitmapDescriptor getThroughPointBitDes() {
        return BitmapDescriptorFactory.fromResource(R.drawable.car);
    }

    private BitmapDescriptor getDriveBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.car);
    }

    /**
     * 给起点Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    private BitmapDescriptor getStartBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.start);
    }

    /**
     * 给终点Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     *
     * @return 更换的Marker图片。
     */
    private BitmapDescriptor getEndBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.end);
    }

    private void addPolyLine(PolylineOptions options) {
        if(options == null) {
            return;
        }
        Polyline polyline = mAMap.addPolyline(options);
        if(polyline != null) {
            allPolyLines.add(polyline);
        }
    }

    private int getDriveColor() {
        return Color.parseColor("#537edc");
    }

    private LatLng getPointForDis(LatLng sPt, LatLng ePt, double dis) {
        double lSegLength = AMapUtils.calculateLineDistance(sPt, ePt);
        double preResult = dis / lSegLength;
        return new LatLng((ePt.latitude - sPt.latitude) * preResult + sPt.latitude, (ePt.longitude - sPt.longitude) * preResult + sPt.longitude);
    }

    public void setThroughPointIconVisibility(boolean visible) {
        try {
            throughPointMarkerVisible = visible;
            if (this.mThroughPointMarkerList != null
                    && this.mThroughPointMarkerList.size() > 0) {
                for (int i = 0; i < this.mThroughPointMarkerList.size(); i++) {
                    this.mThroughPointMarkerList.get(i).setVisible(visible);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动镜头到当前的视角。
     * @since V2.1.0
     */
    public void zoomToSpan() {
        if (mStartPoint != null) {
            if (mAMap == null)
                return;
            try {
                LatLngBounds bounds = getLatLngBounds();
                mAMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(bounds, 50));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 路段节点图标控制显示接口。
     * @param visible true为显示节点图标，false为不显示。
     * @since V2.3.1
     */
    public void setNodeIconVisibility(boolean visible) {
        try {
            nodeIconVisible = visible;
            if (this.stationMarkers != null && this.stationMarkers.size() > 0) {
                for (int i = 0; i < this.stationMarkers.size(); i++) {
                    this.stationMarkers.get(i).setVisible(visible);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }



    /**
     * 去掉DriveLineOverlay上的线段和标记。
     */
    public void removeFromMap() {
        try {
            if (mStartMarker != null) {
                mStartMarker.remove();

            }
            if (mEndMarker != null) {
                mEndMarker.remove();
            }
            for (Marker marker : stationMarkers) {
                marker.remove();
            }
            for (Polyline line : allPolyLines) {
                line.remove();
            }
            if (this.mThroughPointMarkerList != null
                    && this.mThroughPointMarkerList.size() > 0) {
                for (int i = 0; i < this.mThroughPointMarkerList.size(); i++) {
                    this.mThroughPointMarkerList.get(i).remove();
                }
                this.mThroughPointMarkerList.clear();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
