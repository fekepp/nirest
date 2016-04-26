package net.fekepp.nirest.device.listeners;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.Cache;
import com.primesense.nite.JointType;
import com.primesense.nite.Point3D;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;
import com.primesense.nite.SkeletonState;
import com.primesense.nite.UserData;
import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTracker.NewFrameListener;
import com.primesense.nite.UserTrackerFrameRef;

import net.fekepp.nirest.model.User;

/**
 * @author "Felix Leif Keppmann"
 */
public class UserTrackerListener implements NewFrameListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Cache<String, User> users;

	boolean onlyOneUserHelper = false;
	short onlyOneUserHelperID = -1;

	@Override
	public void onNewFrame(UserTracker tracker) {

		UserTrackerFrameRef frame = tracker.readFrame();

		Map<String, User> frameUsers = new HashMap<String, User>();

		// FIXME START Temporary to track only one user with ID = 1
		onlyOneUserHelper = false;

		UserData userTmp = null;
		if (onlyOneUserHelperID < 0) {
			userTmp = frame.getUserById(onlyOneUserHelperID);
			if (userTmp != null && userTmp.getSkeleton().getState() != SkeletonState.TRACKED) {
				onlyOneUserHelperID = -1;
				userTmp = null;
			}
		}
		// FIXME END Temporary to track only one user with ID = 1

		for (UserData user : frame.getUsers()) {

			short userId = user.getId();

			if (user.isNew()) {
				tracker.startSkeletonTracking(userId);
				logger.info("Tracking > NiTE ID > {} > NIREST ID > {}", userId, userId - 1);
			}

			// FIXME START Temporary to track only one user with ID = 0
			if (userTmp != null) {
				user = userTmp;
			}

			if (onlyOneUserHelper || user.getSkeleton().getState() != SkeletonState.TRACKED) {
				continue;
			}

			onlyOneUserHelper = true;
			onlyOneUserHelperID = user.getId();
			userId = 1;
			// FIXME END Temporary to track only one user with ID = 0

			net.fekepp.nirest.model.User modelUser = new net.fekepp.nirest.model.User();

			// Center of mass of the joint
			net.fekepp.nirest.model.Coordinate3D modelCenterOfMass = new net.fekepp.nirest.model.Coordinate3D();
			Point3D<Float> centerOfMass = user.getCenterOfMass();
			modelCenterOfMass.setX(centerOfMass.getX());
			modelCenterOfMass.setY(centerOfMass.getY());
			modelCenterOfMass.setZ(centerOfMass.getZ());
			modelUser.setCenterOfMass(modelCenterOfMass);

			Skeleton skeleton = user.getSkeleton();

			net.fekepp.nirest.model.Skeleton modelSkeleton = new net.fekepp.nirest.model.Skeleton();

			// Skeleton state
			modelSkeleton.setState(skeleton.getState().name());

			modelUser.setSkeleton(modelSkeleton);

			if (skeleton.getState() == SkeletonState.TRACKED) {

				// Center of Mass > Neck > Head
				// Center of Mass > Right Shoulder > Right Elbow > Right Hand
				// Center of Mass > Left Shoulder > Left Elbow > Left Hand
				// Center of Mass > Torso
				// Center of Mass > Right Hip > Right Knee > Right Foot
				// Center of Mass > Left Hip > Left Knee > Left Foot
				// net.fekepp.nirest.model.joints.Joint modelJointNeck = null;
				// net.fekepp.nirest.model.joints.Joint modelJointHead = null;
				//
				// net.fekepp.nirest.model.joints.Joint modelJointRightShoulder
				// = null;
				// net.fekepp.nirest.model.joints.Joint modelJointRightElbow =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointRightHand =
				// null;
				//
				// net.fekepp.nirest.model.joints.Joint modelJointLeftShoulder =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointLeftElbow =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointLeftHand =
				// null;
				//
				// net.fekepp.nirest.model.joints.Joint modelJointTorso = null;
				//
				// net.fekepp.nirest.model.joints.Joint modelJointRightHip =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointRightKnee =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointRightFoot =
				// null;
				//
				// net.fekepp.nirest.model.joints.Joint modelJointLeftHip =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointLeftKnee =
				// null;
				// net.fekepp.nirest.model.joints.Joint modelJointLeftFoot =
				// null;

				for (JointType jointType : JointType.values()) {

					SkeletonJoint joint = skeleton.getJoint(jointType);

					// Coordinate of the joint
					net.fekepp.nirest.model.Coordinate3D modelCoordinate = new net.fekepp.nirest.model.Coordinate3D();
					Point3D<Float> position3d = joint.getPosition();

					modelCoordinate.setX(position3d.getX());
					modelCoordinate.setY(position3d.getY());
					modelCoordinate.setZ(position3d.getZ());

					net.fekepp.nirest.model.joints.Joint modelJoint = null;

					switch (jointType.name()) {
					case "HEAD":
						modelJoint = new net.fekepp.nirest.model.joints.Head();
						// modelJointHead = modelJoint;
						break;

					case "NECK":
						modelJoint = new net.fekepp.nirest.model.joints.Neck();
						// modelJointNeck = modelJoint;
						break;

					case "LEFT_SHOULDER":
						modelJoint = new net.fekepp.nirest.model.joints.LeftShoulder();
						// modelJointLeftShoulder = modelJoint;
						break;

					case "RIGHT_SHOULDER":
						modelJoint = new net.fekepp.nirest.model.joints.RightShoulder();
						// modelJointRightShoulder = modelJoint;
						break;

					case "LEFT_ELBOW":
						modelJoint = new net.fekepp.nirest.model.joints.LeftElbow();
						// modelJointLeftElbow = modelJoint;
						break;

					case "RIGHT_ELBOW":
						modelJoint = new net.fekepp.nirest.model.joints.RightElbow();
						// modelJointRightElbow = modelJoint;
						break;

					case "LEFT_HAND":
						modelJoint = new net.fekepp.nirest.model.joints.LeftHand();
						// modelJointLeftHand = modelJoint;
						break;

					case "RIGHT_HAND":
						modelJoint = new net.fekepp.nirest.model.joints.RightHand();
						// modelJointRightHand = modelJoint;
						break;

					case "TORSO":
						modelJoint = new net.fekepp.nirest.model.joints.Torso();
						// modelJointTorso = modelJoint;
						break;

					case "LEFT_HIP":
						modelJoint = new net.fekepp.nirest.model.joints.LeftHip();
						// modelJointLeftHip = modelJoint;
						break;

					case "RIGHT_HIP":
						modelJoint = new net.fekepp.nirest.model.joints.RightHip();
						// modelJointRightHip = modelJoint;
						break;

					case "LEFT_KNEE":
						modelJoint = new net.fekepp.nirest.model.joints.LeftKnee();
						// modelJointLeftKnee = modelJoint;
						break;

					case "RIGHT_KNEE":
						modelJoint = new net.fekepp.nirest.model.joints.RightKnee();
						// modelJointRightKnee = modelJoint;
						break;

					case "LEFT_FOOT":
						modelJoint = new net.fekepp.nirest.model.joints.LeftFoot();
						// modelJointLeftFoot = modelJoint;
						break;

					case "RIGHT_FOOT":
						modelJoint = new net.fekepp.nirest.model.joints.RightFoot();
						// modelJointRightFoot = modelJoint;
						break;

					}

					// Orientation and position confidence
					modelJoint.setOrientationConfidence(joint.getOrientationConfidence());
					modelJoint.setPositionConfidence(joint.getPositionConfidence());
					modelJoint.setCoordinate(modelCoordinate);
					modelSkeleton.getJoints().add(modelJoint);

				}

				// // Center of Mass > Neck > Head
				// modelJointHead.getCoordinate()
				// .setX(modelJointNeck.getCoordinate().getX() -
				// modelJointHead.getCoordinate().getX());
				// modelJointHead.getCoordinate()
				// .setY(modelJointNeck.getCoordinate().getY() -
				// modelJointHead.getCoordinate().getY());
				// modelJointHead.getCoordinate()
				// .setZ(modelJointNeck.getCoordinate().getZ() -
				// modelJointHead.getCoordinate().getZ());
				//
				// modelJointNeck.getCoordinate().setX(modelCenterOfMass.getX()
				// - modelJointNeck.getCoordinate().getX());
				// modelJointNeck.getCoordinate().setY(modelCenterOfMass.getY()
				// - modelJointNeck.getCoordinate().getY());
				// modelJointNeck.getCoordinate().setZ(modelCenterOfMass.getZ()
				// - modelJointNeck.getCoordinate().getZ());
				//
				// // Center of Mass > Right Shoulder > Right Elbow > Right Hand
				// modelJointRightHand.getCoordinate()
				// .setX(modelJointRightElbow.getCoordinate().getX() -
				// modelJointRightHand.getCoordinate().getX());
				// modelJointRightHand.getCoordinate()
				// .setY(modelJointRightElbow.getCoordinate().getY() -
				// modelJointRightHand.getCoordinate().getY());
				// modelJointRightHand.getCoordinate()
				// .setZ(modelJointRightElbow.getCoordinate().getZ() -
				// modelJointRightHand.getCoordinate().getZ());
				//
				// modelJointRightElbow.getCoordinate().setX(
				// modelJointRightShoulder.getCoordinate().getX() -
				// modelJointRightElbow.getCoordinate().getX());
				// modelJointRightElbow.getCoordinate().setY(
				// modelJointRightShoulder.getCoordinate().getY() -
				// modelJointRightElbow.getCoordinate().getY());
				// modelJointRightElbow.getCoordinate().setZ(
				// modelJointRightShoulder.getCoordinate().getZ() -
				// modelJointRightElbow.getCoordinate().getZ());
				//
				// modelJointRightShoulder.getCoordinate()
				// .setX((modelCenterOfMass.getX() -
				// modelJointRightShoulder.getCoordinate().getX()));
				// modelJointRightShoulder.getCoordinate()
				// .setY((modelCenterOfMass.getY() -
				// modelJointRightShoulder.getCoordinate().getY()) * -1);
				// modelJointRightShoulder.getCoordinate()
				// .setZ((modelCenterOfMass.getZ() -
				// modelJointRightShoulder.getCoordinate().getZ()) * -1);
				//
				// // Center of Mass > Left Shoulder > Left Elbow > Left Hand
				// modelJointLeftHand.getCoordinate()
				// .setX(modelJointLeftElbow.getCoordinate().getX() -
				// modelJointLeftHand.getCoordinate().getX());
				// modelJointLeftHand.getCoordinate()
				// .setY(modelJointLeftElbow.getCoordinate().getY() -
				// modelJointLeftHand.getCoordinate().getY());
				// modelJointLeftHand.getCoordinate()
				// .setZ(modelJointLeftElbow.getCoordinate().getZ() -
				// modelJointLeftHand.getCoordinate().getZ());
				//
				// modelJointLeftElbow.getCoordinate().setX(
				// modelJointLeftShoulder.getCoordinate().getX() -
				// modelJointLeftElbow.getCoordinate().getX());
				// modelJointLeftElbow.getCoordinate().setY(
				// modelJointLeftShoulder.getCoordinate().getY() -
				// modelJointLeftElbow.getCoordinate().getY());
				// modelJointLeftElbow.getCoordinate().setZ(
				// modelJointLeftShoulder.getCoordinate().getZ() -
				// modelJointLeftElbow.getCoordinate().getZ());
				//
				// modelJointLeftShoulder.getCoordinate()
				// .setX(modelCenterOfMass.getX() -
				// modelJointLeftShoulder.getCoordinate().getX());
				// modelJointLeftShoulder.getCoordinate()
				// .setY(modelCenterOfMass.getY() -
				// modelJointLeftShoulder.getCoordinate().getY());
				// modelJointLeftShoulder.getCoordinate()
				// .setZ(modelCenterOfMass.getZ() -
				// modelJointLeftShoulder.getCoordinate().getZ());
				//
				// // Center of Mass > Torso
				// modelJointTorso.getCoordinate().setX(modelCenterOfMass.getX()
				// - modelJointTorso.getCoordinate().getX());
				// modelJointTorso.getCoordinate().setY(modelCenterOfMass.getY()
				// - modelJointTorso.getCoordinate().getY());
				// modelJointTorso.getCoordinate().setZ(modelCenterOfMass.getZ()
				// - modelJointTorso.getCoordinate().getZ());
				//
				// // Center of Mass > Right Hip > Right Knee > Right Foot
				// modelJointRightFoot.getCoordinate()
				// .setX(modelJointRightKnee.getCoordinate().getX() -
				// modelJointRightFoot.getCoordinate().getX());
				// modelJointRightFoot.getCoordinate()
				// .setY(modelJointRightKnee.getCoordinate().getY() -
				// modelJointRightFoot.getCoordinate().getY());
				// modelJointRightFoot.getCoordinate()
				// .setZ(modelJointRightKnee.getCoordinate().getZ() -
				// modelJointRightFoot.getCoordinate().getZ());
				//
				// modelJointRightKnee.getCoordinate()
				// .setX(modelJointRightHip.getCoordinate().getX() -
				// modelJointRightKnee.getCoordinate().getX());
				// modelJointRightKnee.getCoordinate()
				// .setY(modelJointRightHip.getCoordinate().getY() -
				// modelJointRightKnee.getCoordinate().getY());
				// modelJointRightKnee.getCoordinate()
				// .setZ(modelJointRightHip.getCoordinate().getZ() -
				// modelJointRightKnee.getCoordinate().getZ());
				//
				// modelJointRightHip.getCoordinate()
				// .setX(modelCenterOfMass.getX() -
				// modelJointRightHip.getCoordinate().getX());
				// modelJointRightHip.getCoordinate()
				// .setY(modelCenterOfMass.getY() -
				// modelJointRightHip.getCoordinate().getY());
				// modelJointRightHip.getCoordinate()
				// .setZ(modelCenterOfMass.getZ() -
				// modelJointRightHip.getCoordinate().getZ());
				//
				// // Center of Mass > Left Hip > Left Knee > Left Foot
				// modelJointLeftFoot.getCoordinate()
				// .setX(modelJointLeftKnee.getCoordinate().getX() -
				// modelJointLeftFoot.getCoordinate().getX());
				// modelJointLeftFoot.getCoordinate()
				// .setY(modelJointLeftKnee.getCoordinate().getY() -
				// modelJointLeftFoot.getCoordinate().getY());
				// modelJointLeftFoot.getCoordinate()
				// .setZ(modelJointLeftKnee.getCoordinate().getZ() -
				// modelJointLeftFoot.getCoordinate().getZ());
				//
				// modelJointLeftKnee.getCoordinate()
				// .setX(modelJointLeftHip.getCoordinate().getX() -
				// modelJointLeftKnee.getCoordinate().getX());
				// modelJointLeftKnee.getCoordinate()
				// .setY(modelJointLeftHip.getCoordinate().getY() -
				// modelJointLeftKnee.getCoordinate().getY());
				// modelJointLeftKnee.getCoordinate()
				// .setZ(modelJointLeftHip.getCoordinate().getZ() -
				// modelJointLeftKnee.getCoordinate().getZ());
				//
				// modelJointLeftHip.getCoordinate()
				// .setX(modelCenterOfMass.getX() -
				// modelJointLeftHip.getCoordinate().getX());
				// modelJointLeftHip.getCoordinate()
				// .setY(modelCenterOfMass.getY() -
				// modelJointLeftHip.getCoordinate().getY());
				// modelJointLeftHip.getCoordinate()
				// .setZ(modelCenterOfMass.getZ() -
				// modelJointLeftHip.getCoordinate().getZ());

				// Center of Mass
				modelCenterOfMass.setX(modelCenterOfMass.getX());
				modelCenterOfMass.setY(modelCenterOfMass.getY());
				modelCenterOfMass.setZ(modelCenterOfMass.getZ());

			}

			frameUsers.put(String.valueOf(userId - 1), modelUser);

			// logger.info("Updated user | with skeleton > {} | {}", userId - 1,
			// skeleton.getState() == SkeletonState.TRACKED);

		}

		synchronized (users) {
			users.invalidateAll();
			users.putAll(frameUsers);
		}

		frame.release();

	}

	public Cache<String, User> getUsers() {
		return users;
	}

	public void setUsers(Cache<String, User> users) {
		this.users = users;
	}

}
