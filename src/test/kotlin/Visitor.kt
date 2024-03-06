import org.assertj.core.api.Assertions
import kotlin.test.Test

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(val costPerYear: Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}

class TimeAndMaterialContract(val costPerHour: Long, val hours: Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}

class SupportContract(val costPerMonth: Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}

interface ReportVisitor<out R> {
    fun visit(contract: FixedPriceContract): R
    fun visit(contract: TimeAndMaterialContract): R
    fun visit(contract: SupportContract): R
}

class MonthlyCostReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long {
        return contract.costPerYear / 12
    }

    override fun visit(contract: TimeAndMaterialContract): Long {
        return contract.costPerHour * contract.hours
    }

    override fun visit(contract: SupportContract): Long {
        return contract.costPerMonth
    }
}

class YearlyReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long {
        return contract.costPerYear
    }

    override fun visit(contract: TimeAndMaterialContract): Long {
        return contract.costPerHour * contract.hours
    }

    override fun visit(contract: SupportContract): Long {
        return contract.costPerMonth * 12
    }
}

class VisitorTest {
    @Test
    fun visitorTest() {
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectGamma = TimeAndMaterialContract(150, 10)
        val projectKappa = TimeAndMaterialContract(50, 50)

        val projects = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)

        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlyCost = projects.sumOf { it.accept(monthlyCostVisitor) }

        println("Monthly Cost : $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(5333)

        //----------------------------------------------------//

        val yearlyCostVisitor = YearlyReportVisitor()
        val yearlyCost = projects.sumOf { it.accept(yearlyCostVisitor) }
        println("Yearly cost: $yearlyCost")
        Assertions.assertThat(yearlyCost).isEqualTo(20_000)
    }
}