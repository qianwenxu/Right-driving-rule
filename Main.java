import java.util.Random;


public class Main {
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//��ʻ���ĳ���
		 int highway_length=8000;//8km
		 int safety_dist=100;//100m
		 int max_car_num=400;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//��೵���ٶ�
		 int overtake_num=0;//��¼��������
		 double is_busy=0.9;
		 int danger_dist=40;//Σ�վ���40m
		 for(int i=0;i<1000;i++){//ʱ��
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>safety_dist)){
				 eachcar[carnum]=new car();
				 eachcar[carnum].dist=0;
				 eachcar[carnum].lane=1;
				 eachcar[carnum].v=(int)Math.floor(r.nextDouble()*16)+17;
				 eachcar[carnum].time=i;
				 //System.out.println(carnum+"�ٶ�:"+eachcar[carnum].v);
				 carnum+=1;
			 }
			 for(int j=outcar;j<carnum;j++){//�Ŷӵĳ�
				 if(eachcar[j].lane==1){
					 eachcar[j].dist+=eachcar[j].v;//�Ҳ೵�� 
				 }else{
					 eachcar[j].dist+=left_v;//���
				 }
				 if(eachcar[j].dist>highway_length){
					 outcar++;
					 eachcar[j].dist=-1;
					 eachcar[j].time=i-eachcar[j].time;
				 }
				 if(j!=0){
					 if(eachcar[j-1].dist-eachcar[j].dist>0&&eachcar[j-1].dist-eachcar[j].dist<=safety_dist&&eachcar[j-1].lane==1&&eachcar[j].lane==1){//j��j-1
						 if(eachcar[j-1].dist-eachcar[j].dist<danger_dist){
							 System.out.println("Σ�գ�");
						 }
						 if(j-2>=outcar){
							 if(eachcar[j-1].v+6<=eachcar[j].v&&eachcar[j-2].dist-eachcar[j].dist>overtaking_dist){
								 eachcar[j].lane=2;
								 overtake_num++;
							 }else{
								 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
								 eachcar[j].v=eachcar[j-1].v;
							 }
						 }else{
							 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
							 eachcar[j].v=eachcar[j-1].v;
						 }
					 }else if(eachcar[j].dist-eachcar[j-1].dist>safety_dist&&eachcar[j].lane==2&&eachcar[j-1].lane==1){//���س���
						 //int tempdist=eachcar[j-1].dist;
						 int tempv=eachcar[j-1].v;
						 eachcar[j-1].dist+=safety_dist;
						 eachcar[j-1].v=eachcar[j].v;
						 eachcar[j].lane=1;
						 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
						 eachcar[j].v=tempv;
					 }
				 }
			 }
		 }
		 double time_ave=0;
		 for(int i=0;i<outcar;i++){
			 time_ave+=eachcar[i].time;
		 }
		 time_ave=time_ave/outcar;
		 System.out.println("���г�������"+carnum);
		 System.out.println("����������"+overtake_num);
		 System.out.println("��ʻ����ͷ��������"+outcar);
		 System.out.println("������θ��ٵ�ƽ��ʱ�䣺"+time_ave); 
	 }
}
