import com.jogamp.opengl.*;
import gmaths.Mat4;
import gmaths.Mat4Transform;

public class Lamp {
    private TransformNode translateX, rotateAll, rotateUpper, rotateback, rootRotate,rotateLamp;
    public SGNode twoBranchRoot;
    private Mat4 changed_Position=Mat4Transform.translate(0,0,0) ;

    private TransformNode makeLightShpere;
    private float xPosition;
    private float yPosition;
    private float zPosition;

    private float randAngle=0;

    private double startTime=0,startTime1=0;

    private float changed_xPosition=0;
    private float changed_zPosition=0;
    private float changed_angle=0;
    private float poseAngle=0;
    private float UpperChangedAngle=0;
    private float lowerChangedAngle=0;
    private float rotateLampAngle=0;
    private ModelNode sphereNode;

    //private TransformNode bulbTransform=new TransformNode("bulbTransform",Mat4Transform.rotateAroundY(0));
    private Mat4 bulbTransform=new Mat4(1);
    //private Mat4 bulbLoc=new Mat4(1);




    private float rotateAllAngleStart = 12, rotateAllAngle = rotateAllAngleStart;
    private float rotateUpperAngleStart = -12, rotateUpperAngle = rotateUpperAngleStart;
    private float rootAngle=0;
    private boolean jump=false;
    private boolean rotateBeforeJump=false;
    private boolean randRotate=false;
    //private ModelNode LampCom;

    public Lamp(Model Lampholder,Model LampCom,Model bulb,Model lamptail,float xPosition1,float yPosition1,float zPosition1)
    {
        this.xPosition=xPosition1;
        this.yPosition=yPosition1;
        this.zPosition=zPosition1+3;
        twoBranchRoot = new NameNode("two-branch structure");
        //NameNode root=new NameNode("root");
        translateX = new TransformNode("translate("+xPosition+",0,0)", Mat4Transform.translate(xPosition,yPosition,zPosition));

        rootRotate= new TransformNode("rotate root",Mat4Transform.rotateAroundY(0));
        rotateAll = new TransformNode("rotateAroundZ("+rotateAllAngle+")", Mat4Transform.rotateAroundZ(rotateAllAngle));
        rotateback=new TransformNode("rotateAroundZ("+-rotateAllAngle+")",Mat4Transform.rotateAroundZ(-rotateAllAngle));
        NameNode rootBranch=new NameNode("rootbranch");
        Mat4 m1 = Mat4Transform.scale(1.5f,0.2f,1.5f);
        m1 = Mat4.multiply(m1, Mat4Transform.translate(0,0.5f,0));
        TransformNode makerootBranch=new TransformNode("scale(2.5,4,2.5); translate(0,0.5,0)", m1);
        ModelNode cube0Node=new ModelNode("cube",LampCom);
        NameNode lowerBranch = new NameNode("lower branch");
        Mat4 m2 = Mat4Transform.scale(0.2f,1.5f,0.2f);
        m2 = Mat4.multiply(m2, Mat4Transform.translate(0,0.5f,0));
        TransformNode makeLowerBranch = new TransformNode("scale(2.5,4,2.5); translate(0,0.5,0)", m2);
        ModelNode cube1Node = new ModelNode("Sphere(0)", Lampholder);
        TransformNode translateToTop = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0,1.5f,0));
        TransformNode translatetotop1=new TransformNode("translate(0,0.5,0)",Mat4Transform.translate(0,1.5f,0));
        NameNode midnode=new NameNode("midnode");
        m1=Mat4Transform.scale(0.5f,0.5f,0.5f);
        //m1=Mat4.multiply(m1,Mat4Transform.translate(0,3,0));
        TransformNode makemidNode=new TransformNode("scale 0.5",m1);


        ModelNode cube2Node=new ModelNode("cube",Lampholder);
        //TransformNode translateToTop1 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0,0.5f,0));
        rotateUpper = new TransformNode("rotateAroundZ("+rotateUpperAngle+")",Mat4Transform.rotateAroundZ(rotateUpperAngle));
        NameNode upperBranch = new NameNode("upper branch");
        m1 = Mat4Transform.scale(0.2f,1.5f,0.2f);
        m1= Mat4.multiply(m1, Mat4Transform.translate(0,0.5f,0));
        TransformNode makeUpperBranch = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", m1);
        ModelNode cube3Node = new ModelNode("Sphere(1)", Lampholder);

        NameNode lamp=new NameNode("lamp");
        m1=Mat4Transform.scale(3f,0.2f,0.2f);
        m1=Mat4Transform.scale(1f,0.6f,0.6f);
        TransformNode makeLamp=new TransformNode("scale 0.5",m1);
        ModelNode cube4Node=new ModelNode("cube",LampCom);

        NameNode tail=new NameNode("lamp");
        m1=Mat4Transform.scale(2f,0.2f,0.2f);
        m1= Mat4.multiply(Mat4Transform.rotateAroundZ(30),m1);
        m1= Mat4.multiply(Mat4Transform.translate(-1.2f,-0.7f,0),m1);
        TransformNode maketail=new TransformNode("scale 0.5",m1);
        ModelNode tailNode=new ModelNode("cube",lamptail);

        NameNode lightsphere=new NameNode("light sphere");
        m1=Mat4Transform.scale(0.3f,0.3f,0.3f);
        m1=Mat4.multiply(Mat4Transform.translate(0.6f,0,0),m1);
        makeLightShpere=new TransformNode("scale 0.2",m1);
        sphereNode=new ModelNode("sphere",bulb);
        //ModelNode lightNode=new ModelNode("light",lamplight);

        rotateLamp=new TransformNode("rotateAroundZ",Mat4Transform.rotateAroundY(0));
        twoBranchRoot.addChild(translateX);
        translateX.addChild(rootRotate);
        rootRotate.addChild(rootBranch);
        rootBranch.addChild(makerootBranch);
        makerootBranch.addChild(cube0Node);
        rootBranch.addChild(lowerBranch);
        lowerBranch.addChild(rotateAll);
        rotateAll.addChild(makeLowerBranch);
        makeLowerBranch.addChild(cube1Node);


        rotateAll.addChild(translateToTop);
        translateToTop.addChild(midnode);
        midnode.addChild(rotateback);
        rotateback.addChild(makemidNode);
        makemidNode.addChild(cube2Node);
        rotateback.addChild(upperBranch);
        upperBranch.addChild(rotateUpper);
        rotateUpper.addChild(makeUpperBranch);
        makeUpperBranch.addChild(cube3Node);
        rotateUpper.addChild(translatetotop1);
        translatetotop1.addChild(lamp);
        lamp.addChild(rotateLamp);
        rotateLamp.addChild(makeLamp);
        //lamp.addChild(makeLamp);
        makeLamp.addChild(cube4Node);
        rotateLamp.addChild(lightsphere);
        lightsphere.addChild(makeLightShpere);
        makeLightShpere.addChild(sphereNode);
        rotateLamp.addChild(tail);
        tail.addChild(maketail);
        maketail.addChild(tailNode);
        twoBranchRoot.update();  // IMPORTANT – must be done every time any part of the scene graph changes
    }

    //neccesary get and set 
    public void setBulbTransform()
    {
        bulbTransform=sphereNode.worldTransform;
    }

    public Mat4 getBulbTransform()
    {
        setBulbTransform();
        return bulbTransform;
    }

    public void setStartTime(double startTime)
    {
        this.startTime=startTime;
    }

    public void setStartTime1(double startTime1)
    {
        this.startTime1=startTime1;
    }

    public void setRandAngle(float randAngle)
    {
        this.randAngle=randAngle;
    }

    public boolean getJump()
    {
        return this.jump;
    }

    public void setPoseAngle(float poseAngle)
    {
        this.poseAngle=poseAngle;
    }

    public void setRotateBeforeJump(boolean rotateBeforeJump){
        this.rotateBeforeJump=rotateBeforeJump;
    }

    public boolean getRotateBeforeJump()
    {
        return this.rotateBeforeJump;
    }

    public void setRandRotate(boolean randRotate)
    {
        this.randRotate=randRotate;
    }

    public  boolean getRandRotate()
    {
        return this.randRotate;
    }

    
    //when we click jump, random rotate triggered firstly
    private double elapsedTime1;
    public void rotate()
    {
        elapsedTime1 = getSeconds() - startTime;

        if(60*elapsedTime1<=Math.abs(randAngle))
            rootRotate.setTransform(Mat4Transform.rotateAroundY(60*(float)elapsedTime1+changed_angle));
        else
        {
            rootAngle=60*(float)elapsedTime1+changed_angle;
            rootAngle = (float)Math.toRadians(rootAngle);
            startTime=getSeconds();//set the start time for translate
            jump=true;
            rotateBeforeJump=false;
            changed_angle+=60*elapsedTime1;
        }
        twoBranchRoot.update(); // 

    }

    //jump to anywhere
    public void updateBranches() {

        double elapsedTime = getSeconds() - startTime;
        float distance=2*(float) (elapsedTime);
        float xPosition1=0;
        float zPosition1=0;
        if(xPosition+changed_xPosition+1.2f*(float) (2*Math.cos(rootAngle))<9&&1.2f*(float)(2*Math.cos(rootAngle))+xPosition+changed_xPosition>-9&&zPosition+changed_zPosition-1.2f*(float)(2*Math.sin(rootAngle))>1.2&&zPosition+changed_zPosition-1.2f*(float)(2*Math.sin(rootAngle))<12.5 ) {
            xPosition1 = 1.2f * (float) (distance * Math.cos(rootAngle));//60* elapes
            zPosition1 = -1.2f * (float) (distance * Math.sin(rootAngle));
        }
        else
        {
            xPosition1=0;
            zPosition1=0;
        }

        if (elapsedTime < 1) {
            translateX.setTransform(Mat4.multiply(Mat4Transform.translate(xPosition1 + xPosition, (float) (Math.sin(distance * 3.141 / 2) + yPosition), zPosition + zPosition1), changed_Position));//rootRotate doesn't change, so we don't have to multiply rootRotate
            rotateAllAngle = rotateAllAngleStart * (1 + 1.2f*(float) Math.sin(elapsedTime * 3f));
            rotateUpperAngle = rotateUpperAngleStart * (1 + 1.2f*(float) Math.sin(elapsedTime * 3f));
            rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
            rotateback.setTransform(Mat4Transform.rotateAroundZ(-rotateAllAngle));
            rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
            twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
        }
        else{
            changed_xPosition+=xPosition1;
            changed_zPosition+=zPosition1;
            changed_Position=Mat4Transform.translate(changed_xPosition,0,changed_zPosition);
            jump=false;
        }

    }

    //random pose
    private double gapTime;
    public void updateBranchesRan(){
        if(poseAngle+ rotateAllAngleStart +lowerChangedAngle<0||poseAngle+ rotateAllAngleStart +lowerChangedAngle>80)
        {poseAngle=poseAngle/10;}
        gapTime=getSeconds()-startTime1;
        //System.out.println(poseAngle);
        float currentAngle=30f*(float)gapTime;
        float currentLampAngle=1.2f*(float)Math.sin(poseAngle)*currentAngle;
        if(poseAngle>0) {
            if (currentAngle <= Math.abs(poseAngle)) {
                rotateAllAngle = rotateAllAngleStart + currentAngle + lowerChangedAngle;
                rotateUpperAngle = rotateUpperAngleStart - currentAngle - UpperChangedAngle;

                rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
                rotateback.setTransform(Mat4Transform.rotateAroundZ(-rotateAllAngle));
                rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
                rotateLamp.setTransform(Mat4Transform.rotateAroundY(currentLampAngle+rotateLampAngle));
                twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
            } else {
                lowerChangedAngle += currentAngle;
                UpperChangedAngle += currentAngle;
                rotateLampAngle+=currentLampAngle;
                randRotate = false;
                System.out.println("Loweerchanged angle="+lowerChangedAngle);
            }
        }
        else
        {
            currentAngle=-currentAngle;
            if (Math.abs(currentAngle) <= Math.abs(poseAngle)) {
                rotateAllAngle = rotateAllAngleStart + currentAngle + lowerChangedAngle;
                rotateUpperAngle = rotateUpperAngleStart - currentAngle - UpperChangedAngle;
                rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
                rotateback.setTransform(Mat4Transform.rotateAroundZ(-rotateAllAngle));
                rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
                rotateLamp.setTransform(Mat4Transform.rotateAroundY(2f*(float)Math.cos(poseAngle)*currentAngle+rotateLampAngle));
                twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
            } else {
                lowerChangedAngle += currentAngle;
                UpperChangedAngle += currentAngle;
                rotateLampAngle+=1.5f*(float)Math.cos(poseAngle)*currentAngle;
                randRotate = false;
            }
        }

    }

    //reset
    public void reset()
    {
        jump=false;
        rotateBeforeJump=false;
        randRotate=false;
        translateX.setTransform(Mat4Transform.translate(xPosition, yPosition, zPosition));
        rotateAllAngle = rotateAllAngleStart;
        rotateUpperAngle = rotateUpperAngleStart;
        lowerChangedAngle=0;
        UpperChangedAngle=0;
        rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
        rotateback.setTransform(Mat4Transform.rotateAroundZ(-rotateAllAngle));
        rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
        rotateLamp.setTransform(Mat4Transform.rotateAroundY(rotateAllAngle));
        changed_xPosition=0;
        changed_zPosition=0;
        rootAngle=0;
        rootRotate.setTransform(Mat4Transform.rotateAroundY(0));
        changed_Position=Mat4Transform.translate(changed_xPosition,0,changed_zPosition);
        twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
        changed_angle=0;
        poseAngle=0;
        rotateLampAngle=0;
    }

    private double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }
}
