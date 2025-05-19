package vn.com.pharmacity.config.service.impl;

import com.miragesql.miragesql.DefaultEntityOperator;
import com.miragesql.miragesql.SqlExecutor;
import com.miragesql.miragesql.SqlManagerImpl;
import com.miragesql.miragesql.bean.BeanDescFactory;
import com.miragesql.miragesql.naming.DefaultNameConverter;
import com.miragesql.miragesql.type.BigDecimalValueType;
import com.miragesql.miragesql.type.BooleanPrimitiveValueType;
import com.miragesql.miragesql.type.BooleanValueType;
import com.miragesql.miragesql.type.ByteArrayValueType;
import com.miragesql.miragesql.type.DoublePrimitiveValueType;
import com.miragesql.miragesql.type.DoubleValueType;
import com.miragesql.miragesql.type.FloatPrimitiveValueType;
import com.miragesql.miragesql.type.FloatValueType;
import com.miragesql.miragesql.type.IntegerPrimitiveValueType;
import com.miragesql.miragesql.type.IntegerValueType;
import com.miragesql.miragesql.type.LongPrimitiveValueType;
import com.miragesql.miragesql.type.LongValueType;
import com.miragesql.miragesql.type.ShortPrimitiveValueType;
import com.miragesql.miragesql.type.ShortValueType;
import com.miragesql.miragesql.type.SqlDateValueType;
import com.miragesql.miragesql.type.StringValueType;
import com.miragesql.miragesql.type.TimeValueType;
import com.miragesql.miragesql.type.TimestampValueType;
import com.miragesql.miragesql.type.UtilDateValueType;
import com.miragesql.miragesql.type.enumerate.EnumOneBasedOrdinalValueType;
import com.miragesql.miragesql.type.enumerate.EnumOrdinalValueType;
import com.miragesql.miragesql.type.enumerate.EnumStringValueType;

import vn.com.pharmacity.config.service.SqlManagerService;

public class SqlManagerServiceImpl extends SqlManagerImpl implements SqlManagerService {

    @Override
    public Long getNextValBySeqName(String seqName) {
        String querySql = dialect.getSequenceSql(seqName);
        Long sequenceValue = super.sqlExecutor.getSingleResult(Long.class, querySql, new Object[0]);
        return sequenceValue;
    }
    public SqlManagerServiceImpl() {
        super();
    }
    public SqlManagerServiceImpl(SqlExecutor sqlExecutor) {
        super.sqlExecutor = sqlExecutor;
        super.addValueType(new StringValueType());
        super.addValueType(new IntegerValueType());
        super.addValueType(new IntegerPrimitiveValueType());
        super.addValueType(new LongValueType());
        super.addValueType(new LongPrimitiveValueType());
        super.addValueType(new ShortValueType());
        super.addValueType(new ShortPrimitiveValueType());
        super.addValueType(new DoubleValueType());
        super.addValueType(new DoublePrimitiveValueType());
        super.addValueType(new FloatValueType());
        super.addValueType(new FloatPrimitiveValueType());
        super.addValueType(new BooleanValueType());
        super.addValueType(new BooleanPrimitiveValueType());
        super.addValueType(new BigDecimalValueType());
        super.addValueType(new SqlDateValueType());
        super.addValueType(new UtilDateValueType());
        super.addValueType(new TimeValueType());
        super.addValueType(new TimestampValueType());
        super.addValueType(new ByteArrayValueType());
        super.addValueType(new EnumStringValueType());
        super.addValueType(new EnumOrdinalValueType());
        super.addValueType(new EnumOneBasedOrdinalValueType());
        // addValueType(new com.miragesql.miragesql.type.DefaultValueType());

        super.setDialect(dialect);
        super.setBeanDescFactory(new BeanDescFactory());
        super.setNameConverter(new DefaultNameConverter());
        super.setEntityOperator(new DefaultEntityOperator());

        //
    }

}
