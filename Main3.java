import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

class myComparator implements Comparator<car>{
	 @Override
	 public int compare(car o1, car o2) {
	//���n1С��n2�����Ǿͷ�����ֵ�����n1����n2���Ǿͷ��ظ�ֵ��//�����ߵ�һ�£��Ϳ���ʵ�ַ���������
		 if(o1.dist < o2.dist) { 
			 return 1;
	     }else if(o1.dist > o2.dist){
	    	 return -1;
	     }else {
	    	 return 0;
	     }
	    }    
	 }
public class Main3 {
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
	 static boolean is_overtaking_need(int j,car[] eachcar,int rightbefore,int []danger_times,int []not_danger_times){
		 double safety_dist=eachcar[j].sd+10;
		 double danger_dist=eachcar[j].sd;
		 if(rightbefore!=-1){
			 if(eachcar[rightbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[rightbefore].v<eachcar[j].want_v){
				 if(eachcar[rightbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 //System.out.println("Σ�գ�");
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
				 leftbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 int leftafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==2){
					 leftafter=s;//���г�
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
				 rightbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(rightbefore==-1||eachcar[rightbefore].dist-eachcar[j].dist>safety_dist){
			 int rightafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==1){
					 rightafter=s;//���г�
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
	 static double is_left_following(int j,car[] eachcar,int carnum,int outcar,int []danger_times,int [] not_danger_times){
		 double danger_dist=eachcar[j].sd;
		 double safety_dist=eachcar[j].sd+10;
		 int leftbefore=-1;//��¼���ǰ��
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 return 0;
		 }else{
			 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist){
				 danger_times[0]++;
				 //System.out.println("Σ�գ�");
			 }else{
				 not_danger_times[0]++;
			 }
			 return eachcar[leftbefore].v;
		 }
		 
	 }
	 public static void main(String[] args){
		 int highway_length=20000;//8km
		 //int safety_dist=100;//100m
		 int max_car_num=3200;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//��೵���ٶ�
		 //int danger_dist=40;//Σ�վ���40m
		 int onecarnum=0;
		 int oneovertake_num=0;
		 int onereturn_num=0;
		 int oneoutcar=0;
		 int oneleft_out=0;
		 float dng_ratio=0;
		 double onetime_ave=0;
		 for(int k=0;k<100;k++){
			 int carnum=0;
			 int outcar=0;//��ʻ���ĳ���
			 int overtake_num=0;//��¼��������
			 int return_num=0;
			 double is_busy=0.023;
			 int danger_times[]={0};
			 int  not_danger_times[]={0};
			 int left_out=0;
			 for(int i=0;i<3600*24;i++){//ʱ��
				 Random r = new Random();
				 double u = r.nextDouble();
				 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>eachcar[carnum-1].sd||eachcar[carnum-1].dist==-1)){
					 eachcar[carnum]=new car();
					 eachcar[carnum].dist=0;
					 eachcar[carnum].lane=1;
					 eachcar[carnum].v=gaussrand((double)27.8,3);
					 if(eachcar[carnum].v<16.7){
						 eachcar[carnum].v=16.6;
					 }
					 if(eachcar[carnum].v>33.3){
						 eachcar[carnum].v=33.3;
					 }
					 eachcar[carnum].want_v=eachcar[carnum].v;
					 eachcar[carnum].i=carnum;
					 eachcar[carnum].tr=r.nextDouble();
					 eachcar[carnum].sd=eachcar[carnum].tr*eachcar[carnum].v+0.063828*eachcar[carnum].v*eachcar[carnum].v;
					 eachcar[carnum].time=i;
					 //System.out.println(carnum+"�ٶ�:"+eachcar[carnum].v);
					 carnum+=1;
				 }
				 for(int j=outcar;j<carnum;j++){//�Ŷӵĳ�
					 if(eachcar[j].lane==1){
						 eachcar[j].dist+=eachcar[j].v;//�Ҳ೵�� 
						 if(eachcar[j].dist>highway_length){
							 outcar++;
							 eachcar[j].dist=-1;
							 eachcar[j].time=i-eachcar[j].time;
							 if(eachcar[j].lane==2){left_out++;}
							 continue;
						 }
						 int rightbefore=-1;//��¼�Ҳ�ǰ��
						 if(j!=0){
							 for(int s=j-1;s>=outcar;s--){
								 if(eachcar[s].lane==1){
									 rightbefore=s;//ǰ���г�
									 break;
								 }
							 }
							 if(is_overtaking_need(j,eachcar,rightbefore,danger_times, not_danger_times)){
								 if(is_to_left(j,eachcar,carnum,outcar)){
									 eachcar[j].lane=2;
									 eachcar[j].v=eachcar[rightbefore].v+6;
									 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
									 overtake_num++;
									 //System.out.println("��"+i+"��"+eachcar[j].i+"����");
								 }else{
									 if(rightbefore!=-1){
										 eachcar[j].v = eachcar[rightbefore].v;
										 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
									 }
								 }
							 }
						 }
					 }else{
						 eachcar[j].dist+=left_v;//���
						 if(eachcar[j].dist>highway_length){
							 outcar++;
							 eachcar[j].dist=-1;
							 eachcar[j].time=i-eachcar[j].time;
							 if(eachcar[j].lane==2){left_out++;}
							 continue;
						 }
						 int rightbefore=-1;//��¼�Ҳ�ǰ��
						 for(int s=j-1;s>=outcar;s--){
							 if(eachcar[s].lane==1){
								 rightbefore=s;//ǰ���г�
								 break;
							 }
						 }
						 if(is_overtaking_need(j,eachcar,rightbefore,danger_times, not_danger_times)){
							 double following_v=is_left_following(j,eachcar,carnum,outcar,danger_times, not_danger_times);
							 if(following_v!=0){
								 eachcar[j].v=following_v;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
							 }
						 }else{
							 if(is_to_right(j,eachcar,carnum,outcar)){
								 eachcar[j].lane=1;
								 eachcar[j].v=eachcar[j].want_v;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 return_num++;
								 //System.out.println("��"+i+"����"+eachcar[j].i+"���ص��Ҳ࣬����Ϊ��"+j+"��");
							 }else{
								 double following_v=is_left_following(j,eachcar,carnum,outcar,danger_times, not_danger_times);
								 if(following_v!=0){
									 eachcar[j].v=following_v;
									 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 } 
							 }
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
			 onecarnum+=carnum;
			 oneovertake_num+=overtake_num;
			 onereturn_num+=return_num;
			 oneoutcar+=outcar;
			 oneleft_out+=left_out;
			 dng_ratio+=(float)danger_times[0]/(float)(not_danger_times[0]+danger_times[0]);
			 onetime_ave+=time_ave; 
		 }
		 System.out.println("���г�������"+onecarnum/(float)100);
		 System.out.println("����������"+oneovertake_num/(float)100);
		 System.out.println("�������ȥ�Ĵ�����"+onereturn_num/(float)100);
		 System.out.println("��ʻ����ͷ(��������)��������"+oneoutcar/(float)100);
		 System.out.println("�󳵵���ȥ��������"+oneleft_out/(float)100);
		 System.out.println("Σ�ձ�����"+(float)dng_ratio/(float)100);
		 System.out.println("������θ��ٵ�ƽ��ʱ�䣺"+onetime_ave/(float)100); 
	 }
}
