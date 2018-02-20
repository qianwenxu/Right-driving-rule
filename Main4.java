import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main4 {
	static double gaussrand(double E, double V) {
    	double V1, V2 = 0, S = 0;
    	int phase = 0;
    	double X;
    	Random r = new Random();
    	if (phase == 0) {
    		do {
    			double U1 = (double)r.nextDouble();
    			double U2 = (double)r.nextDouble();
    			V1 = 2 * U1 - 1;
    			V2 = 2 * U2 - 1;
    			S = V1 * V1 + V2 * V2;
    		} while (S >= 1 || S == 0);
    		X = V1 * Math.sqrt(-2 * Math.log(S) / S);
    	}
    	else X = V2 * Math.sqrt(-2 * Math.log(S) / S);
    	phase = 1 - phase;
    	return X * V + E;
    	}
	 static boolean is_right_overtaking_need(int j,car[] eachcar,int rightbefore,int []danger_times,int []not_danger_times){
		 double safety_dist=eachcar[j].sd+10;
		 double danger_dist=eachcar[j].sd;
		 if(rightbefore!=-1){
			 if(eachcar[rightbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[rightbefore].v<eachcar[j].want_v){
				 if(eachcar[rightbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 //System.out.println("危险！");
					 danger_times[0]++;
				 }else{
					 not_danger_times[0]++;
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_left_overtaking_need(int j,car[] eachcar,int leftbefore,int []danger_times,int []not_danger_times){
		 double safety_dist=eachcar[j].sd+10;
		 double danger_dist=eachcar[j].sd;
		 if(leftbefore!=-1){
			 if(eachcar[leftbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[leftbefore].v<eachcar[j].want_v){
				 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 //System.out.println("危险！");
					 danger_times[0]++;
				 }else{
					 not_danger_times[0]++;
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_left(int j,car[] eachcar,int carnum,int outcar){
		 double safety_dist=eachcar[j].sd+10;
		 int leftbefore=-1;
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//前方有车
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 int leftafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==2){
					 leftafter=s;//后方有车
					 break;
				 }
			 }
			 if(leftafter==-1||eachcar[j].dist-eachcar[leftafter].dist-eachcar[leftafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_right(int j,car[] eachcar,int carnum,int outcar){
		 double safety_dist=eachcar[j].sd+10;
		 int rightbefore=-1;
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==1){
				 rightbefore=s;//前方有车
				 break;
			 }
		 }
		 if(rightbefore==-1||eachcar[rightbefore].dist-eachcar[j].dist>safety_dist){
			 int rightafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==1){
					 rightafter=s;//后方有车
					 break;
				 }
			 }
			 if(rightafter==-1||eachcar[j].dist-eachcar[rightafter].dist-eachcar[rightafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 /*static int is_left_following(int j,car[] eachcar,int carnum,int outcar,int []danger_times,int [] not_danger_times){
		 double danger_dist=eachcar[j].sd;
		 double safety_dist=eachcar[j].sd+10;
		 int leftbefore=-1;//记录左侧前方
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//前方有车
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 return 0;
		 }else{
			 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist){
				 danger_times[0]++;
				 //System.out.println("危险！");
			 }else{
				 not_danger_times[0]++;
			 }
			 return eachcar[leftbefore].v;
		 }
		 
	 }*/
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//行驶过的车号
		 int highway_length=8000;//8km
		 //int safety_dist=100;//100m
		 int max_car_num=3500;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//左侧车道速度
		 int overtake_num=0;//记录超车辆数
		 int return_num=0;
		 double is_busy=0.005;
		 int danger_times[]={0};
		 int  not_danger_times[]={0};
		 int left_out=0;
		 //int danger_dist=40;//危险距离40m
		 int left_in=0;
		 int right_in=0;
		 for(int i=0;i<3600*24;i++){//时间
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>eachcar[carnum-1].sd||eachcar[carnum-1].dist==-1)){
				 eachcar[carnum]=new car();
				 eachcar[carnum].dist=0;
				 if(r.nextDouble()<0.55){
					 eachcar[carnum].lane=1; 
					 right_in++;
				 }else{
					 eachcar[carnum].lane=2; 
					 left_in++;
				 }
				 eachcar[carnum].v=gaussrand((double)25,2.8);
				 if(eachcar[carnum].v<16.7){
					 eachcar[carnum].v=16.7;
				 }
				 if(eachcar[carnum].v>33.3){
					 eachcar[carnum].v=33.3;
				 }
				 eachcar[carnum].want_v=eachcar[carnum].v;
				 eachcar[carnum].i=carnum;
				 eachcar[carnum].tr=r.nextDouble();
				 eachcar[carnum].sd=eachcar[carnum].tr*eachcar[carnum].v+0.004925*eachcar[carnum].v*eachcar[carnum].v;
				 eachcar[carnum].time=i;
				 //System.out.println(carnum+"速度:"+eachcar[carnum].v);
				 carnum+=1;
			 }
			 for(int j=outcar;j<carnum;j++){//排队的车
				 if(eachcar[j].lane==1){
					 eachcar[j].dist+=eachcar[j].v;//右侧车道 
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 eachcar[j].time=i-eachcar[j].time;
						 if(eachcar[j].lane==2){left_out++;}
						 continue;
					 }
					 int rightbefore=-1;//记录右侧前方
					 if(j!=0){
						 for(int s=j-1;s>=outcar;s--){
							 if(eachcar[s].lane==1){
								 rightbefore=s;//前方有车
								 break;
							 }
						 }
						 if(is_right_overtaking_need(j,eachcar,rightbefore,danger_times, not_danger_times)){
							 if(is_to_left(j,eachcar,carnum,outcar)){
								 eachcar[j].lane=2;
								 eachcar[j].v=eachcar[j].v+6;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 overtake_num++;
								 //System.out.println("第"+i+"秒"+eachcar[j].i+"超车");
							 }else{
								 if(rightbefore!=-1){
									 eachcar[j].v = eachcar[rightbefore].v;
									 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 }
							 }
						 }else{
							 eachcar[j].v=eachcar[j].want_v;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
						 }
					 }
				 }else{
					 eachcar[j].dist+=left_v;//左侧
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 eachcar[j].time=i-eachcar[j].time;
						 if(eachcar[j].lane==2){left_out++;}
						 continue;
					 }
					 int leftbefore=-1;//记录左侧前方
					 for(int s=j-1;s>=outcar;s--){
						 if(eachcar[s].lane==2){
							 leftbefore=s;//前方有车
							 break;
						 }
					 }
					 if(is_left_overtaking_need(j,eachcar,leftbefore,danger_times, not_danger_times)){
						 if(is_to_right(j,eachcar,carnum,outcar)){
							 eachcar[j].lane=1;
							 eachcar[j].v=eachcar[j].v+6;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
							 overtake_num++;
							 //System.out.println("第"+i+"秒"+eachcar[j].i+"超车");
						 }else{
							 if(leftbefore!=-1){
								 eachcar[j].v = eachcar[leftbefore].v;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
							 }
						 }
					 }else{
						 eachcar[j].v=eachcar[j].want_v;
						 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
					 }
				 }
				 Comparator cmp = new myComparator();
			     Arrays.sort(eachcar,outcar,carnum,cmp);
			 }
		 }
		 double time_ave=0;
		 for(int i=0;i<outcar;i++){
			 time_ave+=eachcar[i].time;
		 }
		 time_ave=time_ave/outcar;
		 System.out.println("共有车辆数："+carnum);
		 System.out.println("左进："+left_in);
		 System.out.println("右进："+right_in);
		 System.out.println("超车次数："+overtake_num);
		 //System.out.println("超车后回去的次数："+return_num);
		 System.out.println("行驶到尽头(包括左右)车辆数："+outcar);
		 //System.out.println("左车道出去车辆数："+left_out);
		 System.out.println("危险比例："+(float)danger_times[0]/(float)(not_danger_times[0]+danger_times[0]));
		 System.out.println("经过这段高速的平均时间："+time_ave); 
	 }
}
